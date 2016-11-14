package com.project.jbehave;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.FilePrintStreamFactory.ResolveToPackagedName;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import com.project.bddsimple.features.calculator.eBiblioEnSteps;

import java.net.URL;
import java.util.Locale;
import java.util.Properties;

import static org.jbehave.core.reporters.Format.*;

public class bddSimpleEmbedder extends Embedder {

    //public static final String VARIABLE = "&";
    public static final String VARIABLE = "$";
    private final CrossReference xref = new CrossReference();

    private final String STORY_LOCATION = "com/test/projet/stories/**/c_*.story";


    @Override
    public EmbedderControls embedderControls() {
        return new EmbedderControls().doGenerateViewAfterStories(true)
                .doIgnoreFailureInStories(false)
                .doIgnoreFailureInView(false)
                .useThreads(1).useStoryTimeoutInSecs(60);
    }

    @Override
    public Configuration configuration() {
        //Keywords frKeywords = new LocalizedKeywords(Locale.FRENCH);
        Keywords frKeywords = new LocalizedKeywords(Locale.ENGLISH);
        //Class<? extends Embeddable> embeddableClass = this.getClass();
        Class<? extends bddSimpleEmbedder> embedderClass = this.getClass();
        URL codeLocation = CodeLocations.codeLocationFromClass(embedderClass);
        Properties viewResources = new Properties();
        viewResources.put("decorateNonHtml", "true");
        // Start from default ParameterConverters instance
        ParameterConverters parameterConverters = new ParameterConverters();
        // factory to allow parameter conversion and loading from external
        // resources (used by StoryParser too)
        // ExamplesTableFactory examplesTableFactory = new ExamplesTableFactory(new LocalizedKeywords(Locale.FRENCH),
        // new LoadFromClasspath(embeddableClass), parameterConverters);
        // add custom converters
        //parameterConverters.addConverters(new DateConverter(new SimpleDateFormat("yyyy-MM-dd")))
                /*
                                                                                                * , new
                                                                                                * ExamplesTableConverter
                                                                                                * (examplesTableFactory)
                                                                                                */
        ;

        return new MostUsefulConfiguration()
                .useKeywords(frKeywords)
                //.useStoryControls(new StoryControls().doDryRun(false).doSkipScenariosAfterFailure(false))
                .useStoryLoader(new LoadFromClasspath(embedderClass))
                .useStoryParser(new RegexStoryParser(frKeywords))
                .useStoryPathResolver(new UnderscoredCamelCaseResolver())
                .useStoryReporterBuilder(
                        new StoryReporterBuilder()
                                .withCodeLocation(codeLocation)
                                .withDefaultFormats().withPathResolver(new ResolveToPackagedName())
                                .withViewResources(viewResources).withFormats(CONSOLE, TXT, HTML, XML)
                                .withFailureTrace(true).withFailureTraceCompression(true).withCrossReference(xref))
                .useParameterConverters(parameterConverters)
                .usePendingStepStrategy(new FailingUponPendingStep())
                // use '%' instead of '$' to identify parameters
                .useStepPatternParser(new RegexPrefixCapturingPatternParser(VARIABLE)).useStepMonitor(xref.getStepMonitor());
    }





  
    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new eBiblioEnSteps());
    }

}