package elshab7.engineering.elshab7_rss_news_app;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;

public class RSS_App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                                             .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                                             .setResizeAndRotateEnabledForNetwork(true)
                                             .setDownsampleEnabled(true)
                                             .build();

        Fresco.initialize(this,config);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
