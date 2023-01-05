package br.com.inatel.Tasty.karate;

import com.intuit.karate.junit5.Karate;

class EvaluationRunner {
    @Karate.Test
    Karate testEvaluation() {
        return Karate.run("karate").relativeTo(getClass());
    }
}
