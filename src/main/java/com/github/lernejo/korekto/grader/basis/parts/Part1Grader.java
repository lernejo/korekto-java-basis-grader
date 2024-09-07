package com.github.lernejo.korekto.grader.basis.parts;

import com.github.lernejo.korekto.grader.basis.LaunchingContext;
import com.github.lernejo.korekto.toolkit.GradePart;
import com.github.lernejo.korekto.toolkit.PartGrader;
import com.github.lernejo.korekto.toolkit.thirdparty.git.GitContext;
import com.github.lernejo.korekto.toolkit.thirdparty.git.GitNature;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.List;
import java.util.stream.Collectors;

public record Part1Grader(String name, Double maxGrade,
                          Double individualCommitGrade) implements PartGrader<LaunchingContext> {

    private static final List<String> expectedCommitMessages = List.of(
        "Setup project layout",
        "My first class",
        "Setup GitHub CI",
        "Add tests to match 100% coverage"
    );

    @Override
    public GradePart grade(LaunchingContext context) {
        GitContext gitContext = context.getExercise().lookupNature(GitNature.class).get().getContext();
        List<String> mainCommits = gitContext.listOrderedCommits().stream().map(RevCommit::getShortMessage).toList();

        int validCommitCount = 0;
        for (int commitIndex = 0; commitIndex < Math.min(mainCommits.size(), expectedCommitMessages.size()); commitIndex++) {
            if (expectedCommitMessages.get(commitIndex).equals(mainCommits.get(commitIndex))) {
                validCommitCount++;
            } else {
                break;
            }
        }
        if (mainCommits.size() == expectedCommitMessages.size() && validCommitCount == mainCommits.size()) {
            return result(List.of(), maxGrade());
        } else if (mainCommits.size() > expectedCommitMessages.size()) {
            return result(List.of("Too much commits: %s (%s / %s valid)"
                .formatted(mainCommits.size(), validCommitCount, expectedCommitMessages.size())), validCommitCount * individualCommitGrade);
        } else if (mainCommits.size() < expectedCommitMessages.size()) {
            return result(List.of("Missing %s commits (%s / %s valid)"
                .formatted(expectedCommitMessages.size() - mainCommits.size(), validCommitCount, expectedCommitMessages.size())), validCommitCount * individualCommitGrade);
        } else {
            String formattedExpectedCommits = expectedCommitMessages.stream().collect(Collectors.joining("\n        * ", "\n        * ", "\n"));
            String formattedActualCommits = mainCommits.stream().collect(Collectors.joining("\n        * ", "\n        * ", "\n"));
            String explanations = "Expecting commits to be" + formattedExpectedCommits + "but was" + formattedActualCommits;
            return result(List.of(explanations), validCommitCount * individualCommitGrade);
        }
    }
}
