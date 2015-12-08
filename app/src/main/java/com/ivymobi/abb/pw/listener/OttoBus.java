package com.ivymobi.abb.pw.listener;

import com.squareup.otto.Bus;

import org.androidannotations.annotations.EBean;

/**
 * Created by fengchen on 15/12/8.
 */
// Declare the bus as an enhanced bean
@EBean(scope = EBean.Scope.Singleton)
public class OttoBus extends Bus {

}
