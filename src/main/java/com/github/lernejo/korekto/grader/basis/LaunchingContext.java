package com.github.lernejo.korekto.grader.basis;

import com.github.lernejo.korekto.toolkit.GradingConfiguration;
import com.github.lernejo.korekto.toolkit.GradingContext;
import com.github.lernejo.korekto.toolkit.partgrader.MavenContext;

public class LaunchingContext extends GradingContext implements MavenContext {
    private boolean compilationFailed;
    private boolean testFailed;

    public LaunchingContext(GradingConfiguration configuration) {
        super(configuration);
    }

    @Override
    public boolean hasCompilationFailed() {
        return compilationFailed;
    }

    @Override
    public boolean hasTestFailed() {
        return testFailed;
    }

    @Override
    public void markAsCompilationFailed() {
        compilationFailed = true;
    }

    @Override
    public void markAsTestFailed() {
        testFailed = true;
    }
}
