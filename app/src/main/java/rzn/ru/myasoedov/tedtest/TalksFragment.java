package rzn.ru.myasoedov.tedtest;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import rzn.ru.myasoedov.tedtest.adapter.EndlessScrollListener;
import rzn.ru.myasoedov.tedtest.adapter.TalkAdapter;
import rzn.ru.myasoedov.tedtest.dto.ResponseWrapper;
import rzn.ru.myasoedov.tedtest.dto.TalkInfo;
import rzn.ru.myasoedov.tedtest.loader.TalksLoader;

/**
 * Created by grisha on 03.05.15.
 */
public class TalksFragment extends ListFragment implements LoaderManager.LoaderCallbacks<ResponseWrapper<List<TalkInfo>>> {
    private static final int TALKS_LOADER_ID = 1;
    private String search;
    private List<TalkInfo> talks;
    private boolean addTalks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        addTalks = true;
        talks = new LinkedList<TalkInfo>();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyText(getResources().getString(R.string.no_talks));
        getListView().setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
               restartLoader();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().initLoader(TALKS_LOADER_ID, null, this);
        if (getActivity().getActionBar() != null) {
            getActivity().getActionBar().show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_ted, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        prepareSearchView(menuItem);
    }

    private void prepareSearchView(MenuItem menuItem) {
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search = query;
                resetList();
                restartLoader();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search = newText;
                return false;
            }
        });

        if (!TextUtils.isEmpty(search)) {
            searchView.post(new Runnable() {
                @Override
                public void run() {
                    searchView.setQuery(search, false);
                }
            });
        }
    }

    private void restartLoader() {
        addTalks = true;
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(search)) {
            bundle.putString(TalksLoader.NAME, search + "*");
        }
        bundle.putInt(TalksLoader.OFFSET, talks.size());
        getLoaderManager().restartLoader(TALKS_LOADER_ID, bundle, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                resetList();
                restartLoader();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resetList() {
        addTalks = true;
        talks.clear();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TalkInfo item = (TalkInfo) getListAdapter().getItem(position);
        VideoFragment videoFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(VideoFragment.TALK_ID, item.getTalk().getId());
        videoFragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, videoFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public Loader<ResponseWrapper<List<TalkInfo>>> onCreateLoader(int id, Bundle bundle) {
        Loader<ResponseWrapper<List<TalkInfo>>> loader = null;
        if (id == TALKS_LOADER_ID) {
            loader = new TalksLoader(this.getActivity(), bundle);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ResponseWrapper<List<TalkInfo>>> loader,
                               ResponseWrapper<List<TalkInfo>> data) {
        if (data.getException() != null) {
            Toast.makeText(this.getActivity(), data.getException().getMessage(), Toast.LENGTH_LONG)
                    .show();
        } else {
            if (getListAdapter() == null) {
                setListAdapter(new TalkAdapter(getActivity(), talks));
            }
            if (addTalks) {
                ((TalkAdapter) getListAdapter()).addTalks(data.getResponse());
            }
            addTalks = false;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

}