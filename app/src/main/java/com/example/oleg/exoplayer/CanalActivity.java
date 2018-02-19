package com.example.oleg.exoplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaCodec;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer.DefaultLoadControl;
import com.google.android.exoplayer.ExoPlaybackException;
import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.LoadControl;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecSelector;
import com.google.android.exoplayer.MediaCodecVideoTrackRenderer;
import com.google.android.exoplayer.TrackRenderer;
import com.google.android.exoplayer.chunk.Format;
import com.google.android.exoplayer.hls.DefaultHlsTrackSelector;
import com.google.android.exoplayer.hls.HlsChunkSource;
import com.google.android.exoplayer.hls.HlsMasterPlaylist;
import com.google.android.exoplayer.hls.HlsPlaylist;
import com.google.android.exoplayer.hls.HlsPlaylistParser;
import com.google.android.exoplayer.hls.HlsSampleSource;
import com.google.android.exoplayer.hls.PtsTimestampAdjusterProvider;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.util.ManifestFetcher;
import com.google.android.exoplayer.util.PlayerControl;
import com.google.android.exoplayer.util.Util;

import java.io.IOException;

public class CanalActivity extends AppCompatActivity implements ManifestFetcher.ManifestCallback<HlsPlaylist>,
        ExoPlayer.Listener,HlsSampleSource.EventListener, AudioManager.OnAudioFocusChangeListener, View.OnClickListener {

    private SurfaceView surface;
    private Button btn_play, btn_pause, btn_left, btn_right;
    private ExoPlayer player;
    private PlayerControl playerControl;
    private String video_url;
    private Handler mainHandler;
    private AudioManager am;
    private String userAgent;
    private ManifestFetcher<HlsPlaylist> playlistFetcher;
    private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
    private static final int MAIN_BUFFER_SEGMENTS = 254;
    public static final int TYPE_VIDEO = 0;
    private TextView txt_playState;
    private TrackRenderer videoRenderer;
    private MediaCodecAudioTrackRenderer audioRenderer;

    public int regulator;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canal);

        activity=this;

        //Toast.makeText(getApplicationContext(), "You Selected " + String.valueOf(regulator), Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        String leftRightStr = intent.getExtras().getString("lrStr");

        playExoPlayer(leftRightStr);


/*
        surface = (SurfaceView) findViewById(R.id.surface_view); // we import surface
        txt_playState = (TextView) findViewById(R.id.txt_playstate);
        btn_play = (Button) findViewById(R.id.btn_play);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this); // we init buttons and listners
        player = ExoPlayer.Factory.newInstance(2);
        playerControl = new PlayerControl(player); // we init player
        //https://developer.apple.com/videos/play/wwdc2017/504/
        video_url = "http://api.new.livestream.com/accounts/22711876/events/6759790/live.m3u8"; //video url
        ///
        //video_url = "http://playertest.longtailvideo.com/adaptive/bbbfull/bbbfull.m3u8"; //video url
        //1.http://api.new.livestream.com/accounts/22711876/events/6759790/live.m3u8
        //2.http://hls.ksl.com/t/KSL_NEWSRADIO/playlist.m3u8
        am = (AudioManager) this.getApplicationContext().getSystemService(Context.AUDIO_SERVICE); // for requesting audio
        mainHandler = new Handler(); //handler required for hls
        userAgent = Util.getUserAgent(this, "MainActivity"); //useragent required for hls
        HlsPlaylistParser parser = new HlsPlaylistParser(); // init HlsPlaylistParser
        playlistFetcher = new ManifestFetcher<>(video_url, new DefaultUriDataSource(this, userAgent),
                parser); // url goes here, useragent and parser
        playlistFetcher.singleLoad(mainHandler.getLooper(), this); //with 'this' we'll implement ManifestFetcher.ManifestCallback<HlsPlaylist>
*/

        //listener with it will come two functions
        ///btn_left
        btn_left=(Button)findViewById(R.id.btn_left);
        btn_left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String leftStr  = "http://api.new.livestream.com/accounts/22711876/events/6759790/live.m3u8";

                Intent i = new Intent(getApplicationContext(), CanalActivity.class);
                i.putExtra("lrStr", leftStr);
                startActivity(i);

            }
        });


        ///btn_right
        btn_right=(Button)findViewById(R.id.btn_right);
        btn_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String leftStr  = "http://hls.ksl.com/t/KSL_NEWSRADIO/playlist.m3u8";

                Intent i = new Intent(getApplicationContext(), CanalActivity.class);
                i.putExtra("lrStr", leftStr);
                startActivity(i);

            }
        });

    }
    //inside onSingleManifest we'll code to play hls
    @Override
    public void onSingleManifest(HlsPlaylist manifest) {
        LoadControl loadControl = new DefaultLoadControl(new DefaultAllocator(BUFFER_SEGMENT_SIZE));
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        PtsTimestampAdjusterProvider timestampAdjusterProvider = new PtsTimestampAdjusterProvider();
        boolean haveSubtitles = false;
        boolean haveAudios = false;
        if (manifest instanceof HlsMasterPlaylist) {
            HlsMasterPlaylist masterPlaylist = (HlsMasterPlaylist) manifest;
            haveSubtitles = !masterPlaylist.subtitles.isEmpty();

        }
        // Build the video/id3 renderers.
        DataSource dataSource = new DefaultUriDataSource(this, bandwidthMeter, userAgent);
        HlsChunkSource chunkSource = new HlsChunkSource(true /* isMaster */, dataSource, manifest,
                DefaultHlsTrackSelector.newDefaultInstance(this), bandwidthMeter,
                timestampAdjusterProvider, HlsChunkSource.ADAPTIVE_MODE_SPLICE);
        HlsSampleSource sampleSource = new HlsSampleSource(chunkSource, loadControl,
                MAIN_BUFFER_SEGMENTS * BUFFER_SEGMENT_SIZE, mainHandler, this, TYPE_VIDEO);
        MediaCodecVideoTrackRenderer videoRenderer = new MediaCodecVideoTrackRenderer(this, sampleSource,
                MediaCodecSelector.DEFAULT, MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        MediaCodecAudioTrackRenderer audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource,
                MediaCodecSelector.DEFAULT);
        this.videoRenderer = videoRenderer;
        this.audioRenderer = audioRenderer;
        pushSurface(false); // here we pushsurface
        player.prepare(videoRenderer,audioRenderer); //prepare
        player.addListener(this); //add listener for the text field
        if (requestFocus())
            player.setPlayWhenReady(true);
    }
    public boolean requestFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
                am.requestAudioFocus(CanalActivity.this, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
    }
    private void pushSurface(boolean blockForSurfacePush) {
        if (videoRenderer == null) {return;}
        if (blockForSurfacePush) {
            player.blockingSendMessage(
                    videoRenderer, MediaCodecVideoTrackRenderer.MSG_SET_SURFACE, surface.getHolder().getSurface());
        } else {
            player.sendMessage(
                    videoRenderer, MediaCodecVideoTrackRenderer.MSG_SET_SURFACE, surface.getHolder().getSurface());
        }
    }

    @Override
    public void onSingleManifestError(IOException e) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        String text = "";
        switch (playbackState) {
            case ExoPlayer.STATE_BUFFERING:
                text += "buffering";
                break;
            case ExoPlayer.STATE_ENDED:
                text += "ended";
                break;
            case ExoPlayer.STATE_IDLE:
                text += "idle";
                break;
            case ExoPlayer.STATE_PREPARING:
                text += "preparing";
                break;
            case ExoPlayer.STATE_READY:
                text += "ready";
                break;
            default:
                text += "unknown";
                break;
        }
        txt_playState.setText(text);

        //for the text feild
    }

    @Override
    public void onPlayWhenReadyCommitted() {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onLoadStarted(int sourceId, long length, int type, int trigger, Format format, long mediaStartTimeMs, long mediaEndTimeMs) {

    }

    @Override
    public void onLoadCompleted(int sourceId, long bytesLoaded, int type, int trigger, Format format, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs) {

    }

    @Override
    public void onLoadCanceled(int sourceId, long bytesLoaded) {

    }

    @Override
    public void onLoadError(int sourceId, IOException e) {

    }

    @Override
    public void onUpstreamDiscarded(int sourceId, long mediaStartTimeMs, long mediaEndTimeMs) {

    }

    @Override
    public void onDownstreamFormatChanged(int sourceId, Format format, int trigger, long mediaTimeMs) {

    }

    @Override
    public void onAudioFocusChange(int focusChange) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pause:
                playerControl.pause();
                break;
            case R.id.btn_play:
                playerControl.start();
                break;
         }
        //for play and pause
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //TOKEN
        if (id == R.id.some_info) {
            Toast.makeText(getApplicationContext(), "some information about the app", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), InformationActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void playExoPlayer(String video_urlStr) {

        surface = (SurfaceView) findViewById(R.id.surface_view); // we import surface
        txt_playState = (TextView) findViewById(R.id.txt_playstate);
        btn_play = (Button) findViewById(R.id.btn_play);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this); // we init buttons and listners
        player = ExoPlayer.Factory.newInstance(2);
        playerControl = new PlayerControl(player); // we init player

        video_url = video_urlStr;

        //https://developer.apple.com/videos/play/wwdc2017/504/
        //video_url = "http://api.new.livestream.com/accounts/22711876/events/6759790/live.m3u8"; //video url
        ///
        //video_url = "http://playertest.longtailvideo.com/adaptive/bbbfull/bbbfull.m3u8"; //video url
        //1.http://api.new.livestream.com/accounts/22711876/events/6759790/live.m3u8
        //2.http://hls.ksl.com/t/KSL_NEWSRADIO/playlist.m3u8
        am = (AudioManager) this.getApplicationContext().getSystemService(Context.AUDIO_SERVICE); // for requesting audio
        mainHandler = new Handler(); //handler required for hls
        userAgent = Util.getUserAgent(this, "CanalActivity"); //useragent required for hls
        HlsPlaylistParser parser = new HlsPlaylistParser(); // init HlsPlaylistParser
        playlistFetcher = new ManifestFetcher<>(video_url, new DefaultUriDataSource(this, userAgent),
                parser); // url goes here, useragent and parser
        playlistFetcher.singleLoad(mainHandler.getLooper(), this); //with 'this' we'll implement ManifestFetcher.ManifestCallback<HlsPlaylist>

    }

    ///
    /*
    class CastOptionsProvider implements OptionsProvider {
        @Override
        public CastOptions getCastOptions(Context context) {
            CastOptions castOptions = new CastOptions.Builder()
                    .setReceiverApplicationId(context.getString(R.string.app_id))
                    .build();
            return castOptions;
        }
        @Override
        public List<SessionProvider> getAdditionalSessionProviders(Context context) {
            return null;
        }
    }*/

    public int getReg(int i) {
        return i;
    }

}