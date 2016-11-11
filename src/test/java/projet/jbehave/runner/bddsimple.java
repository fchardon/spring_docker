package projet.jbehave.runner;

import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import projet.jbehave.bddSimpleEmbedder;

import java.net.URL;
import java.util.List;

public class bddsimple {

    private final String STORY_LOCATION = "com/test/projet/stories/**/c_*.story";


    protected List<String> storyPaths() {
        URL searchInURL = CodeLocations.codeLocationFromClass(this.getClass());
        return new StoryFinder().findPaths(searchInURL, STORY_LOCATION, "");
    }

    //@Test
    public void runClasspathLoadedStoriesAsJUnit() {
        // Embedder defines the configuration and candidate steps
        Embedder embedder = new bddSimpleEmbedder();
        List<String> storyPaths = storyPaths(); // use StoryFinder to look up paths
        embedder.runStoriesAsPaths(storyPaths);
    }

}