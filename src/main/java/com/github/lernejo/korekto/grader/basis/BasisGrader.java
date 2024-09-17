package com.github.lernejo.korekto.grader.basis;

import com.github.lernejo.korekto.grader.basis.parts.Part1Grader;
import com.github.lernejo.korekto.toolkit.Grader;
import com.github.lernejo.korekto.toolkit.GradingConfiguration;
import com.github.lernejo.korekto.toolkit.PartGrader;
import com.github.lernejo.korekto.toolkit.partgrader.GitHubActionsPartGrader;
import com.github.lernejo.korekto.toolkit.partgrader.JacocoCoveragePartGrader;
import com.github.lernejo.korekto.toolkit.partgrader.MavenCompileAndTestPartGrader;

import java.util.Collection;
import java.util.List;

public class BasisGrader implements Grader<LaunchingContext> {

    @Override
    public String name() {
        return "korekto-java-basis-grader";
    }

    @Override
    public String slugToRepoUrl(String login) {
        return "https://github.com/" + login + "/java_basis_training";
    }

    @Override
    public LaunchingContext gradingContext(GradingConfiguration configuration) {
        return new LaunchingContext(configuration);
    }

    @Override
    public void run(LaunchingContext context) {
        context.getGradeDetails().getParts().addAll(grade(context));
    }

    public Collection<PartGrader<LaunchingContext>> graders() {
        return List.of(
            new Part1Grader("Part 1 - Git History", 4.0D, 0.5D),
            new MavenCompileAndTestPartGrader<>(
                "Part 2 - Compilation Maven",
                2.0D),
            new GitHubActionsPartGrader<>("Part 3 - GitHub CI", 2.0D),
            new JacocoCoveragePartGrader<>("Part 4 - 100% test coverage", 6, 1.0D)
        );
    }

    @Override
    public boolean needsWorkspaceReset() {
        return true;
    }
}
