package recipeEvaluation;

import com.intuit.karate.junit5.Karate;

class recipeEvaluationRunner {

    @Karate.Test
    Karate testEvaluations() {
        return Karate.run("recipeEvaluation").relativeTo(getClass());
    }  
}
