package org.sample.currency.app;

import cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by Mohamed Mekkawy.
 */
@RunWith(Cucumber.class)
@Cucumber.Options(
        features = "src/test/features"
        ,glue={"org.sample.currency.app.acceptance"}
)
public class BDDTestRunner {

}
