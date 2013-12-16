/*
 * (C) 2013 axn software UG
 * TODO:LICENSE All Rights Reserved.
 */
package de.axnsoftware.settings.integration.customtype;

import de.axnsoftware.settings.SettingsStoreFactory;
import java.io.File;
import org.junit.Test;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public class CustomTypeTest
{

    @Test
    public void settingsStoreFactoryMustNotFailOnCustomType()
    {
        SettingsStoreFactory.newInstance().newFileStore(new File("/tmp/unused"),
                                                        SettingsRoot.class);
    }
}
