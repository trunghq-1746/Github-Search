package tech.ericntd.githubsearch.search;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import tech.ericntd.githubsearch.models.SearchResponse;
import tech.ericntd.githubsearch.models.SearchResult;
import tech.ericntd.githubsearch.repositories.GitHubApi;
import tech.ericntd.githubsearch.repositories.GitHubRepository;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SearchPresenterTest {

    /*@Mock
    private GitHubApi api;

    @InjectMocks
    private GitHubRepository repository;*/

    @Mock
    private GitHubRepository repository;

    /*@Mock
    private GitHubApi api;

    @Spy
    private GitHubRepository repository = new GitHubRepository(api);*/

    @Mock
    private SearchViewContract viewContract;

    @Spy
    private SearchPresenter presenter = new SearchPresenter(viewContract, repository);

    @Mock
    private Response<SearchResponse> response;

    @Mock
    private SearchResponse searchResponse;

    @Before
    public void init() {
    }

    @Test
    public void searchGitHubRepos_noQuery() {
        String searchQuery = null;
        presenter.searchGitHubRepos(searchQuery);
        verify(repository, times(0)).searchRepos(searchQuery, presenter);
    }

    @Test
    public void searchGitHubRepos() {
        String searchQuery = "AroundHere";
        presenter.searchGitHubRepos(searchQuery);
        verify(repository, times(1)).searchRepos(searchQuery, presenter);
    }

    @Test
    public void handleGitHubResponse() {
        doReturn(true).when(response).isSuccessful();
        doReturn(searchResponse).when(response).body();
        List<SearchResult> searchResults = new ArrayList();
        searchResults.add(new SearchResult());
        searchResults.add(new SearchResult());
        searchResults.add(new SearchResult());
        doReturn(searchResults).when(searchResponse).getSearchResults();
        doReturn(searchResults.size()).when(searchResponse).getTotalCount();
        presenter.handleGitHubResponse(response);
        verify(viewContract, times(1)).displaySearchResults(searchResults, searchResults.size());
    }

    @Test
    public void handleGitHubError() {
    }
}