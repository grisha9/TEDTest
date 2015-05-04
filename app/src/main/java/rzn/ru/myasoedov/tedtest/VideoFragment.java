package rzn.ru.myasoedov.tedtest;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import rzn.ru.myasoedov.tedtest.dto.Media;
import rzn.ru.myasoedov.tedtest.dto.ResponseWrapper;
import rzn.ru.myasoedov.tedtest.dto.TalkInfo;
import rzn.ru.myasoedov.tedtest.loader.TalkLoader;

/**
 * Created by grisha on 03.05.15.
 */
public class VideoFragment extends Fragment implements LoaderManager.LoaderCallbacks<ResponseWrapper<TalkInfo>> {
    public static final String TALK_ID = "id";
    private static final int TALK_LOADER_ID = 1;

    private int currentPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ted, container, false);

        VideoView videoView = (VideoView) view.findViewById(R.id.video);
        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                setProgressBarVisibility(false);
            }
        });

        if (getActivity().getActionBar() != null) {
            getActivity().getActionBar().hide();
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putLong(TalkLoader.ID, getArguments().getLong(TALK_ID));
        getLoaderManager().initLoader(TALK_LOADER_ID, bundle, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        VideoView videoView = (VideoView) getView().findViewById(R.id.video);
        videoView.pause();
        currentPosition = videoView.getCurrentPosition();
    }

    @Override
    public Loader<ResponseWrapper<TalkInfo>> onCreateLoader(int id, Bundle bundle) {
        Loader<ResponseWrapper<TalkInfo>> loader = null;
        if (id == TALK_LOADER_ID) {
            loader = new TalkLoader(this.getActivity(), bundle);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ResponseWrapper<TalkInfo>> loader, ResponseWrapper<TalkInfo> data) {
        if (data.getException() != null) {
            setProgressBarVisibility(false);
            Toast.makeText(this.getActivity(), data.getException().getMessage(), Toast.LENGTH_LONG)
                    .show();
        } else {
            VideoView videoView = (VideoView) getView().findViewById(R.id.video);
            Media.Internal media = data.getResponse().getTalk().getMedia().getInternal();
            String url = (TedApplication.isConnectedWifi())
                    ? media.getVideoHeight().getUri()
                    : media.getVideoLow().getUri();
            videoView.setVideoURI(Uri.parse(url));
            if (currentPosition > 0) {
                videoView.seekTo(currentPosition);
            }
            videoView.start();
        }
    }

    @Override
    public void onLoaderReset(Loader<ResponseWrapper<TalkInfo>> loader) {

    }

    private void setProgressBarVisibility(boolean visible) {
        if (getView() != null) {
            getView().findViewById(R.id.progressBar).setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }
}
