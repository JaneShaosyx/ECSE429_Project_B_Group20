package BDDTest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
       /* features = {"src/test/resources/BDDTest/06_get_todo.feature"
               , "src/test/resources/BDDTest/07_delete_category_using_id.feature"
                , "src/test/resources/BDDTest/08_delete_todo_using_id.feature"
                , "src/test/resources/BDDTest/09_get_categories.feature"
                , "src/test/resources/BDDTest/10_get_project.feature"
        },*/
        plugin = {"pretty"})

public class TestRunner {
}

