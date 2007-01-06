package org.commonfarm.security.controller;

import org.commonfarm.security.Initable;

/**
 * The SecurityController allows users to plug in and out a 'controller' class
 * which can enable or disable Seraph, based on some settings in their app.
 *
 * This is most useful for disabling Seraph during certain times - ie during a setup wizard.
 */
public interface SecurityController extends Initable
{
    public static final String NULL_CONTROLLER = NullSecurityController.class.getName();

    public boolean isSecurityEnabled();
}
