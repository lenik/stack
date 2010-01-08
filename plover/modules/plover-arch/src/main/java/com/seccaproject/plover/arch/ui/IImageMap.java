package com.seccaproject.plover.arch.ui;

import java.net.URL;

public interface IImageMap {

    /**
     * 获取默认图像。
     *
     * @return <code>null</code> 若没有可用的图像资源。
     */
    URL getImage();

    /**
     * 获取图像信息。
     * <p>
     * 一个对象可能有多种图像信息，其每种变化成为一个图像变体。一个图像变体可能有更进一步的分化，称为变体细分或子变体。 变体的细分应该用横线 ‘-’
     * 连接，如果某种子变体不存在，方法应该返回存在的最大父变体。 如 <code>locked-active</code>
     * 表示图像的锁定变体、激活子变体，那么当实际上只存在图像的锁定变体时，查询激活子变体应该返回其最大父变体也就是锁定变体。
     *
     * @param variant
     *            图像变体，参考预定义的变体名称。若为 <code>null</code> 表示获取默认图像，等效于 <code>""</code>。
     * @return <code>null</code> 若没有可用的图像资源。
     * @see StandardImageVariant
     */
    URL getImage(String variant);

    /**
     * 获取图像信息。
     * <p>
     * 一个对象可能有多种图像信息，其每种变化成为一个图像变体。一个图像变体可能有更进一步的分化，称为变体细分或子变体。 变体的细分应该用横线 ‘-’
     * 连接，如果某种子变体不存在，方法应该返回存在的最大父变体。 如 <code>locked-active</code>
     * 表示图像的锁定变体、激活子变体，那么当实际上只存在图像的锁定变体时，查询激活子变体应该返回其最大父变体也就是锁定变体。
     *
     * @param variant
     *            图像变体，参考预定义的变体名称。若为 <code>null</code> 表示获取默认图像，等效于 <code>""</code>。
     * @param widthHint
     *            希望显示的图像宽度（像素）0 表示未知。如果实际的图像大小和显示大小不相等，由调用者负责缩放。
     * @param heightHint
     *            希望显示的图像高度（像素）0 表示未知。如果实际的图像大小和显示大小不相等，由调用者负责缩放。
     * @return <code>null</code> 若没有可用的图像资源。
     * @see StandardImageVariant
     */
    URL getImage(String variant, int widthHint, int heightHint);

}
