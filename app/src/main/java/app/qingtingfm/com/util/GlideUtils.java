package app.qingtingfm.com.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


/**
 * @author xyjian
 * @version V1.0.0
 * @Description: {}
 * @date 2018/8/23
 */

public class GlideUtils {


    /**
     * 加载图片 占位图是小号的
     *
     * @param imgUrl    图片url
     * @param imageView 展示view
     */
    public static void loadSmallHolderByUrl(String imgUrl, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .error(R.mipmap.icon_placeholder_gery_small)
//                .placeholder(R.mipmap.icon_placeholder_gery_small)
                .centerCrop()
//                .dontAnimate()
                .into(imageView);

    }

    /**
     * 加载图片 占位图是小号的
     *
     * @param res
     * @param imageView
     */
    public static void loadSmallHolderByRes(Integer res, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(res)
//                .error(R.mipmap.icon_placeholder_gery_small)
                .centerCrop()
//                .dontAnimate()
                .into(imageView);
    }


    /**
     * 加载图片 头像的 占位图是小号的
     *
     * @param imgUrl    图片url
     * @param imageView 展示view
     */
    public static void loadSmallHeaderHolderByUrl(String imgUrl, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(imgUrl)
//                .error(R.mipmap.logo_round)
                .centerCrop()
//                .dontAnimate()
                .into(imageView);
    }

    /**
     * 加载图片 头像的 占位图是小号的
     *
     * @param res
     * @param imageView
     */
    public static void loadSmallHeaderHolderByRes(Integer res, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(res)
//                .error(R.mipmap.logo_round)
                .centerCrop()
//                .dontAnimate()
                .into(imageView);
    }

    /**
     * 加载图片 占位图是中等的
     *
     * @param imgUrl    图片url
     * @param imageView 展示view
     */
    public static void loadMediumHolderByUrl(String imgUrl, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .error(R.mipmap.icon_placeholder_gery_medium)
//                .placeholder(R.mipmap.icon_placeholder_gery_medium)
                .centerCrop()
//                .dontAnimate()
                .into(imageView);
    }

    /**
     * 加载图片 占位图是中等的
     *
     * @param res
     * @param imageView
     */
    public static void loadMediumHolderByRes(Integer res, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(res)
//                .error(R.mipmap.icon_placeholder_gery_medium)
                .centerCrop()
//                .dontAnimate()
                .into(imageView);
    }


    /**
     * 加载图片 占位图是大的
     *
     * @param imageView 展示view
     */
    public static void loadBigHolderByUrl(String imgUrl, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .error(R.mipmap.icon_placeholder_gery_big)
//                .placeholder(R.mipmap.icon_placeholder_gery_big)
                .centerCrop()
//                .dontAnimate()
                .into(imageView);
    }

    /**
     * 加载图片 占位图是大的
     *
     * @param res
     * @param imageView
     */
    public static void loadBigHolderByRes(Integer res, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(res)
//                .error(R.mipmap.icon_placeholder_gery_big)
                .centerCrop()
//                .dontAnimate()
                .into(imageView);
    }


    /**
     * 加载图片 占位图是大的
     *
     * @param imageView 展示view
     */
    public static void loadPlayBigHolderByUrl(String imgUrl, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .error(R.mipmap.icon_placeholder_black_big)
//                .placeholder(R.mipmap.icon_placeholder_black_big)
                .centerCrop()
//                .dontAnimate()
                .into(imageView);
    }


    /**
     * 加载图片 占位图是大的
     *
     * @param res
     * @param imageView
     */
    public static void loadPlayBigHolderByRes(Integer res, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(res)
//                .error(R.mipmap.icon_placeholder_black_big)
                .centerCrop()
//                .dontAnimate()
                .into(imageView);
    }


    /**
     * 加载图片 占位图是大的
     *
     * @param imageView 展示view
     */
    public static void loadPlaySmallHolderByUrl(String imgUrl, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .error(R.mipmap.icon_placeholder_black_small)
//                .placeholder(R.mipmap.icon_placeholder_black_small)
                .centerCrop()
//                .dontAnimate()
                .into(imageView);
    }


    /**
     * 加载图片 占位图是大的
     *
     * @param res
     * @param imageView
     */
    public static void loadPlaySmallHolderByRes(Integer res, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(res)
//                .error(R.mipmap.icon_placeholder_black_small)
                .centerCrop()
//                .dontAnimate()
                .into(imageView);
    }
}
