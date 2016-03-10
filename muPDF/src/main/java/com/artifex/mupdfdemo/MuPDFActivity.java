package com.artifex.mupdfdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.ivymobi.ui.Constants;
import com.ivymobi.ui.HorizontalScrollViewAdapter;
import com.ivymobi.ui.MyHorizontalScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.concurrent.Executor;

class ThreadPerTaskExecutor implements Executor {
    public void execute(Runnable r) {
        new Thread(r).start();
    }
}

public class MuPDFActivity extends Activity {
    /* The core rendering instance */
    enum TopBarMode {
        Main, Search, Annot, Delete, More, Accept
    }
    
    ;
    
    enum AcceptMode {Highlight, Underline, StrikeOut, Ink, CopyText}
    
    ;
    
    private final int OUTLINE_REQUEST = 0;
    private final int PRINT_REQUEST = 1;
    private MuPDFCore core;
    private String mFileName;
    private MuPDFReaderView mDocView;
    
    
    
    
    
    private boolean mButtonsVisible;
    private EditText mPasswordView;
    private TextView mFilenameView;
    private SeekBar mPageSlider;
    private int mPageSliderRes;
    private TextView mPageNumberView;
    private TextView mInfoView;
    private ImageButton mSearchButton;
    private ImageButton mReflowButton;
    private ImageButton mOutlineButton;
    private ImageButton mMoreButton;
    private TextView mAnnotTypeText;
    private ImageButton mAnnotButton;
    private ViewAnimator mTopBarSwitcher;
    private ImageButton mLinkButton;
    private TopBarMode mTopBarMode = TopBarMode.Main;
    private AcceptMode mAcceptMode;
    private ImageButton mSearchBack;
    private ImageButton mSearchFwd;
    private EditText mSearchText;
    private SearchTask mSearchTask;
    private AlertDialog.Builder mAlertBuilder;
    private boolean mLinkHighlight = false;
    private final Handler mHandler = new Handler();
    private boolean mAlertsActive = false;
    private boolean mReflow = false;
    private AsyncTask<Void, Void, MuPDFAlert> mAlertTask;
    private AlertDialog mAlertDialog;
    
    public Activity mLastActivity;
    
    
    public void createAlertWaiter() {
        mAlertsActive = true;
        // All mupdf library calls are performed on asynchronous tasks to avoid stalling
        // the UI. Some calls can lead to javascript-invoked requests to display an
        // alert dialog and collect a reply from the user. The task has to be blocked
        // until the user's reply is received. This method creates an asynchronous task,
        // the purpose of which is to wait of these requests and produce the dialog
        // in response, while leaving the core blocked. When the dialog receives the
        // user's response, it is sent to the core via replyToAlert, unblocking it.
        // Another alert-waiting task is then created to pick up the next alert.
        if (mAlertTask != null) {
            mAlertTask.cancel(true);
            mAlertTask = null;
        }
        if (mAlertDialog != null) {
            mAlertDialog.cancel();
            mAlertDialog = null;
        }
        mAlertTask = new AsyncTask<Void, Void, MuPDFAlert>() {
            
            @Override
            protected MuPDFAlert doInBackground(Void... arg0) {
                if (!mAlertsActive)
                    return null;
                
                return core.waitForAlert();
            }
            
            @Override
            protected void onPostExecute(final MuPDFAlert result) {
                // core.waitForAlert may return null when shutting down
                if (result == null)
                    return;
                final MuPDFAlert.ButtonPressed pressed[] = new MuPDFAlert.ButtonPressed[3];
                for (int i = 0; i < 3; i++)
                    pressed[i] = MuPDFAlert.ButtonPressed.None;
                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog = null;
                        if (mAlertsActive) {
                            int index = 0;
                            switch (which) {
                                case AlertDialog.BUTTON1:
                                    index = 0;
                                    break;
                                case AlertDialog.BUTTON2:
                                    index = 1;
                                    break;
                                case AlertDialog.BUTTON3:
                                    index = 2;
                                    break;
                            }
                            result.buttonPressed = pressed[index];
                            // Send the user's response to the core, so that it can
                            // continue processing.
                            core.replyToAlert(result);
                            // Create another alert-waiter to pick up the next alert.
                            createAlertWaiter();
                        }
                    }
                };
                mAlertDialog = mAlertBuilder.create();
                mAlertDialog.setTitle(result.title);
                mAlertDialog.setMessage(result.message);
                switch (result.iconType) {
                    case Error:
                        break;
                    case Warning:
                        break;
                    case Question:
                        break;
                    case Status:
                        break;
                }
                switch (result.buttonGroupType) {
                    case OkCancel:
                        mAlertDialog.setButton(AlertDialog.BUTTON2, getString(R.string.cancel), listener);
                        pressed[1] = MuPDFAlert.ButtonPressed.Cancel;
                    case Ok:
                        mAlertDialog.setButton(AlertDialog.BUTTON1, getString(R.string.okay), listener);
                        pressed[0] = MuPDFAlert.ButtonPressed.Ok;
                        break;
                    case YesNoCancel:
                        mAlertDialog.setButton(AlertDialog.BUTTON3, getString(R.string.cancel), listener);
                        pressed[2] = MuPDFAlert.ButtonPressed.Cancel;
                    case YesNo:
                        mAlertDialog.setButton(AlertDialog.BUTTON1, getString(R.string.yes), listener);
                        pressed[0] = MuPDFAlert.ButtonPressed.Yes;
                        mAlertDialog.setButton(AlertDialog.BUTTON2, getString(R.string.no), listener);
                        pressed[1] = MuPDFAlert.ButtonPressed.No;
                        break;
                }
                mAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        mAlertDialog = null;
                        if (mAlertsActive) {
                            result.buttonPressed = MuPDFAlert.ButtonPressed.None;
                            core.replyToAlert(result);
                            createAlertWaiter();
                        }
                    }
                });
                
                mAlertDialog.show();
            }
        };
        
        mAlertTask.executeOnExecutor(new ThreadPerTaskExecutor());
    }
    
    public void destroyAlertWaiter() {
        mAlertsActive = false;
        if (mAlertDialog != null) {
            mAlertDialog.cancel();
            mAlertDialog = null;
        }
        if (mAlertTask != null) {
            mAlertTask.cancel(true);
            mAlertTask = null;
        }
    }
    
    private MuPDFCore openFile(String path) {
        int lastSlashPos = path.lastIndexOf('/');
        mFileName = new String(lastSlashPos == -1
                               ? path
                               : path.substring(lastSlashPos + 1));
        System.out.println("Trying to open " + path);
        try {
            core = new MuPDFCore(this, path);
            // New file: drop the old outline data
            OutlineActivityData.set(null);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return core;
    }
    
    private MuPDFCore openBuffer(byte buffer[]) {
        System.out.println("Trying to open byte buffer");
        try {
            core = new MuPDFCore(this, buffer);
            // New file: drop the old outline data
            OutlineActivityData.set(null);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return core;
    }
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mAlertBuilder = new AlertDialog.Builder(this);
        
        if (core == null) {
            core = (MuPDFCore) getLastNonConfigurationInstance();
            
            if (savedInstanceState != null && savedInstanceState.containsKey("FileName")) {
                mFileName = savedInstanceState.getString("FileName");
            }
        }
        if (core == null) {
            Intent intent = getIntent();
            byte buffer[] = null;
            if (Intent.ACTION_VIEW.equals(intent.getAction())) {
                Uri uri = intent.getData();
                
                if (uri.toString().startsWith("content://")) {
                    // Handle view requests from the Transformer Prime's file manager
                    // Hopefully other file managers will use this same scheme, if not
                    // using explicit paths.
                    Cursor cursor = getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
                    if (cursor.moveToFirst()) {
                        String str = cursor.getString(0);
                        String reason = null;
                        if (str == null) {
                            try {
                                InputStream is = getContentResolver().openInputStream(uri);
                                int len = is.available();
                                buffer = new byte[len];
                                is.read(buffer, 0, len);
                                is.close();
                            } catch (OutOfMemoryError e) {
                                System.out.println("Out of memory during buffer reading");
                                reason = e.toString();
                            } catch (Exception e) {
                                reason = e.toString();
                            }
                            if (reason != null) {
                                buffer = null;
                                Resources res = getResources();
                                AlertDialog alert = mAlertBuilder.create();
                                setTitle(String.format(res.getString(R.string.cannot_open_document_Reason), reason));
                                alert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dismiss),
                                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                alert.show();
                                return;
                            }
                        } else {
                            uri = Uri.parse(str);
                        }
                    }
                }
                if (buffer != null) {
                    core = openBuffer(buffer);
                } else {
                    core = openFile(Uri.decode(uri.getEncodedPath()));
                }
                SearchTaskResult.set(null);
            }
            if (core != null && core.needsPassword()) {
                requestPassword(savedInstanceState);
                return;
            }
            if (core != null && core.countPages() == 0) {
                core = null;
            }
        }
        if (core == null) {
            AlertDialog alert = mAlertBuilder.create();
            alert.setTitle(R.string.cannot_open_document);
            alert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dismiss),
                            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
            return;
        }
        
        createUI(savedInstanceState);
    }
    
    public void requestPassword(final Bundle savedInstanceState) {
        mPasswordView = new EditText(this);
        mPasswordView.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        mPasswordView.setTransformationMethod(new PasswordTransformationMethod());
        
        AlertDialog alert = mAlertBuilder.create();
        alert.setTitle(R.string.enter_password);
        alert.setView(mPasswordView);
        alert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.okay),
                        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (core.authenticatePassword(mPasswordView.getText().toString())) {
                    createUI(savedInstanceState);
                } else {
                    requestPassword(savedInstanceState);
                }
            }
        });
        alert.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
            
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.show();
    }
    
    
    public void createUI(Bundle savedInstanceState) {
        if (core == null)
            return;
        
        // Now create the UI.
        // First create the document view
        
        mDocView = new MuPDFReaderView(this) {
            @Override
            protected void onMoveToChild(int i) {
                if (core == null)
                    return;
                super.onMoveToChild(i);
            }
            
            @Override
            protected void onTapMainDocArea() {
                
                if(mScrollView.getVisibility() == View.VISIBLE){
                    mScrollView.setVisibility(View.INVISIBLE);
                }else{
                    mScrollView.setVisibility(View.VISIBLE);
                }
            }
            
            @Override
            protected void onDocMotion() {
                hideButtons();
            }
            
            @Override
            protected void onHit(Hit item) {
                switch (mTopBarMode) {
                    case Annot:
                        if (item == Hit.Annotation) {
                            showButtons();
                            mTopBarMode = TopBarMode.Delete;
                            mTopBarSwitcher.setDisplayedChild(mTopBarMode.ordinal());
                        }
                        break;
                    case Delete:
                        mTopBarMode = TopBarMode.Annot;
                        mTopBarSwitcher.setDisplayedChild(mTopBarMode.ordinal());
                        // fall through
                    default:
                        // Not in annotation editing mode, but the pageview will
                        // still select and highlight hit annotations, so
                        // deselect just in case.
                        MuPDFView pageView = (MuPDFView) mDocView.getDisplayedView();
                        if (pageView != null)
                            pageView.deselectAnnotation();
                        break;
                }
            }
        };
        mDocView.setAdapter(new MuPDFPageAdapter(this, core));
        
        mSearchTask = new SearchTask(this, core) {
            @Override
            protected void onTextFound(SearchTaskResult result) {
                SearchTaskResult.set(result);
                // Ask the ReaderView to move to the resulting page
                mDocView.setDisplayedViewIndex(result.pageNumber);
                // Make the ReaderView act on the change to SearchTaskResult
                // via overridden onChildSetup method.
                mDocView.resetupChildren();
            }
        };
        
        // Reenstate last state if it was recorded
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        
        //        int currIndex = prefs.getInt("page" + mFileName, 0);
        
        int currIndex = 0;
        
        mDocView.setDisplayedViewIndex(currIndex);
        
        createScrollView(currIndex);
        
        
        // Stick the document view and the horizontal_scrollview_land overlay into a parent view
        RelativeLayout layout = new RelativeLayout(this);
        
        //        layout.removeAllViews();
        layout.addView(mDocView);
        layout.addView(mScrollView);
        //        layout.addView(mToolBarView);
        
        
        mScrollView.setVisibility(View.INVISIBLE);
        
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        setContentView(layout);
        
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
        
//        TextView myTitleText = (TextView) findViewById(R.id.pdf_title);
//        if ( myTitleText != null ) {
//
//            String title = getIntent().getStringExtra(Constants.Extra.TITLE);
//            if(title.equals("")){
//                myTitleText.setText(mFileName);
//            }else{
//                myTitleText.setText(title);
//            }
//        }
        
//        ImageButton backBtn = (ImageButton)findViewById(R.id.pdf_back);
//
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onFinish();
//            }
//        });
//
//
//        ImageButton shareBtn = (ImageButton)findViewById(R.id.pdf_share);
//
//        shareBtn.setOnClickListener(new View.OnClickListener() {
//
//
//            JSONObject createShareObject()throws JSONException {
//                JSONObject obj = new JSONObject();
//                obj.put("OnShare", "");
//                return obj;
//            }
//
//            @Override
//            public void onClick(View v) {
//
////                try {
////                    PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, createShareObject());
////                    pluginResult.setKeepCallback(true);
////                    Constants.CallBack.callbackContext.sendPluginResult(pluginResult);
////                }catch (JSONException e){
////
////                }
//
//            }
//        });
    }
    
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case OUTLINE_REQUEST:
                if (resultCode >= 0)
                    mDocView.setDisplayedViewIndex(resultCode);
                break;
            case PRINT_REQUEST:
                if (resultCode == RESULT_CANCELED)
                    showInfo(getString(R.string.print_failed));
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    public Object onRetainNonConfigurationInstance() {
        MuPDFCore mycore = core;
        core = null;
        return mycore;
    }
    
    private void reflowModeSet(boolean reflow) {
        mReflow = reflow;
        mDocView.setAdapter(mReflow ? new MuPDFReflowAdapter(this, core) : new MuPDFPageAdapter(this, core));
        mReflowButton.setColorFilter(mReflow ? Color.argb(0xFF, 172, 114, 37) : Color.argb(0xFF, 255, 255, 255));
        setButtonEnabled(mAnnotButton, !reflow);
        setButtonEnabled(mSearchButton, !reflow);
        if (reflow) setLinkHighlight(false);
        setButtonEnabled(mLinkButton, !reflow);
        setButtonEnabled(mMoreButton, !reflow);
        mDocView.refresh(mReflow);
    }
    
    private void toggleReflow() {
        reflowModeSet(!mReflow);
        showInfo(mReflow ? getString(R.string.entering_reflow_mode) : getString(R.string.leaving_reflow_mode));
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        if (mFileName != null && mDocView != null) {
            outState.putString("FileName", mFileName);
            
            // Store current page in the prefs against the file name,
            // so that we can pick it up each time the file is loaded
            // Other info is needed only for screen-orientation change,
            // so it can go in the bundle
            SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putInt("page" + mFileName, mDocView.getDisplayedViewIndex());
            edit.commit();
        }
        
        if (!mButtonsVisible)
            outState.putBoolean("ButtonsHidden", true);
        
        if (mTopBarMode == TopBarMode.Search)
            outState.putBoolean("SearchMode", true);
        
        if (mReflow)
            outState.putBoolean("ReflowMode", true);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        
        if (mSearchTask != null)
            mSearchTask.stop();
        
        if (mFileName != null && mDocView != null) {
            SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putInt("page" + mFileName, mDocView.getDisplayedViewIndex());
            edit.commit();
        }
    }
    
    public void onDestroy() {
        if (core != null)
            core.onDestroy();
        if (mAlertTask != null) {
            mAlertTask.cancel(true);
            mAlertTask = null;
        }
        core = null;
        super.onDestroy();
    }
    
    private void setButtonEnabled(ImageButton button, boolean enabled) {
        button.setEnabled(enabled);
        button.setColorFilter(enabled ? Color.argb(255, 255, 255, 255) : Color.argb(255, 128, 128, 128));
    }
    
    private void setLinkHighlight(boolean highlight) {
        mLinkHighlight = highlight;
        // LINK_COLOR tint
        mLinkButton.setColorFilter(highlight ? Color.argb(0xFF, 172, 114, 37) : Color.argb(0xFF, 255, 255, 255));
        // Inform pages of the change.
        mDocView.setLinksEnabled(highlight);
    }
    
    private void showButtons() {
        if (core == null)
            return;
        if (!mButtonsVisible) {
            mButtonsVisible = true;
            // Update page number text and slider
            int index = mDocView.getDisplayedViewIndex();
            updatePageNumView(index);
            mPageSlider.setMax((core.countPages() - 1) * mPageSliderRes);
            mPageSlider.setProgress(index * mPageSliderRes);
            if (mTopBarMode == TopBarMode.Search) {
                mSearchText.requestFocus();
                showKeyboard();
            }
            
            Animation anim = new TranslateAnimation(0, 0, -mTopBarSwitcher.getHeight(), 0);
            anim.setDuration(200);
            anim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    mTopBarSwitcher.setVisibility(View.VISIBLE);
                }
                
                public void onAnimationRepeat(Animation animation) {
                }
                
                public void onAnimationEnd(Animation animation) {
                }
            });
            mTopBarSwitcher.startAnimation(anim);
            
            anim = new TranslateAnimation(0, 0, mPageSlider.getHeight(), 0);
            anim.setDuration(200);
            anim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    mPageSlider.setVisibility(View.VISIBLE);
                }
                
                public void onAnimationRepeat(Animation animation) {
                }
                
                public void onAnimationEnd(Animation animation) {
                    mPageNumberView.setVisibility(View.VISIBLE);
                }
            });
            mPageSlider.startAnimation(anim);
        }
    }
    
    private void hideButtons() {
        if (mButtonsVisible) {
            mButtonsVisible = false;
            hideKeyboard();
            
            Animation anim = new TranslateAnimation(0, 0, 0, -mTopBarSwitcher.getHeight());
            anim.setDuration(200);
            anim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }
                
                public void onAnimationRepeat(Animation animation) {
                }
                
                public void onAnimationEnd(Animation animation) {
                    mTopBarSwitcher.setVisibility(View.INVISIBLE);
                }
            });
            mTopBarSwitcher.startAnimation(anim);
            
            anim = new TranslateAnimation(0, 0, 0, mPageSlider.getHeight());
            anim.setDuration(200);
            anim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    mPageNumberView.setVisibility(View.INVISIBLE);
                }
                
                public void onAnimationRepeat(Animation animation) {
                }
                
                public void onAnimationEnd(Animation animation) {
                    mPageSlider.setVisibility(View.INVISIBLE);
                }
            });
            mPageSlider.startAnimation(anim);
        }
    }
    
    private void searchModeOn() {
        if (mTopBarMode != TopBarMode.Search) {
            mTopBarMode = TopBarMode.Search;
            //Focus on EditTextWidget
            mSearchText.requestFocus();
            showKeyboard();
            mTopBarSwitcher.setDisplayedChild(mTopBarMode.ordinal());
        }
    }
    
    private void searchModeOff() {
        if (mTopBarMode == TopBarMode.Search) {
            mTopBarMode = TopBarMode.Main;
            hideKeyboard();
            mTopBarSwitcher.setDisplayedChild(mTopBarMode.ordinal());
            SearchTaskResult.set(null);
            // Make the ReaderView act on the change to mSearchTaskResult
            // via overridden onChildSetup method.
            mDocView.resetupChildren();
        }
    }
    
    private void updatePageNumView(int index) {
        if (core == null)
            return;
        mPageNumberView.setText(String.format("%d / %d", index + 1, core.countPages()));
    }
    
    private void printDoc() {
        if (!core.fileFormat().startsWith("PDF")) {
            showInfo(getString(R.string.format_currently_not_supported));
            return;
        }
        
        Intent myIntent = getIntent();
        Uri docUri = myIntent != null ? myIntent.getData() : null;
        
        if (docUri == null) {
            showInfo(getString(R.string.print_failed));
        }
        
        if (docUri.getScheme() == null)
            docUri = Uri.parse("file://" + docUri.toString());
        
        Intent printIntent = new Intent(this, PrintDialogActivity.class);
        printIntent.setDataAndType(docUri, "aplication/pdf");
        printIntent.putExtra("title", mFileName);
        startActivityForResult(printIntent, PRINT_REQUEST);
    }
    
    private void showInfo(String message) {
        mInfoView.setText(message);
        
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            SafeAnimatorInflater safe = new SafeAnimatorInflater((Activity) this, R.animator.info, (View) mInfoView);
        } else {
            mInfoView.setVisibility(View.VISIBLE);
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    mInfoView.setVisibility(View.INVISIBLE);
                }
            }, 500);
        }
    }
    
    
    void onFinish(){
        this.finish();
    }
    
    
    MyHorizontalScrollView mHorizontalScrollView;
    HorizontalScrollViewAdapter mHorizontalScrollViewAdapter;
    private View mScrollView;
    private View mToolBarView;
    
    //    private TextView mTitle;
    //    private Button mBackBtn;
    //
    //    private void createToolBar(){
    //        mToolBarView = getLayoutInflater().inflate(R.layout.toolbar, null);
    //        mTitle = (TextView)mToolBarView.findViewById(R.id.pdf_title);
    //        mBackBtn = (Button)mToolBarView.findViewById(R.id.backBtn);
    //        String title = getIntent().getStringExtra("title");
    //
    //        mBackBtn.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                onFinish();
    //            }
    //        });
    //
    //
    //        if(title.equals("")){
    //            mTitle.setText(mFileName);
    //        }else{
    //            mTitle.setText(title);
    //        }
    //    }
    
    
    private void createScrollView(int index) {
        
        
        mScrollView = getLayoutInflater().inflate(R.layout.horizontal_scrollview_port, null);
        
        
        
        mHorizontalScrollView = (MyHorizontalScrollView)mScrollView.findViewById(R.id.id_horizontalScrollView);
        mHorizontalScrollViewAdapter = new HorizontalScrollViewAdapter(this, core, index);
        mHorizontalScrollViewAdapter.setBitmapDelegate(new HorizontalScrollViewAdapter.IBitmapGetterDelegate() {
            @Override
            public Bitmap getBitmap(int pos) {
                int w = getResources().getDimensionPixelOffset(R.dimen.thumnail_default_width);
                int h = getResources().getDimensionPixelSize(R.dimen.thumnail_default_height);
                
                Bitmap b = core.drawPage(pos, w, h, 0, 0, w, h);
                
                return b;
            }
        });
        mHorizontalScrollViewAdapter.setScrollView(mHorizontalScrollView);
        mHorizontalScrollView.initDatas(mHorizontalScrollViewAdapter);
        
        
        
        
        mHorizontalScrollView.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener(){
            @Override
            public void onClick(View view, int pos) {
                mDocView.setDisplayedViewIndex(pos);
                mHorizontalScrollView.setSelView(view);
            }
        });
        
    }
    
    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.showSoftInput(mSearchText, 0);
    }
    
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(mSearchText.getWindowToken(), 0);
    }
    
    private void search(int direction) {
        hideKeyboard();
        int displayPage = mDocView.getDisplayedViewIndex();
        SearchTaskResult r = SearchTaskResult.get();
        int searchPage = r != null ? r.pageNumber : -1;
        mSearchTask.go(mSearchText.getText().toString(), direction, displayPage, searchPage);
    }
    
    @Override
    public boolean onSearchRequested() {
        if (mButtonsVisible && mTopBarMode == TopBarMode.Search) {
            hideButtons();
        } else {
            showButtons();
            searchModeOn();
        }
        return super.onSearchRequested();
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mButtonsVisible && mTopBarMode != TopBarMode.Search) {
            hideButtons();
        } else {
            showButtons();
            searchModeOff();
        }
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    protected void onStart() {
        if (core != null) {
            core.startAlerts();
            createAlertWaiter();
        }
        
        super.onStart();
    }
    
    @Override
    protected void onStop() {
        if (core != null) {
            destroyAlertWaiter();
            core.stopAlerts();
        }
        
        super.onStop();
    }
    
    @Override
    public void onBackPressed() {
        if (core.hasChanges()) {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == AlertDialog.BUTTON_POSITIVE)
                        core.save();
                    
                    finish();
                }
            };
            AlertDialog alert = mAlertBuilder.create();
            alert.setTitle("MuPDF");
            alert.setMessage(getString(R.string.document_has_changes_save_them_));
            alert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes), listener);
            alert.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no), listener);
            alert.show();
        } else {
            super.onBackPressed();
        }
    }
}
