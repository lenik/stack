package com.bee32.plover.site;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import javax.free.Dates;
import javax.free.Doc;

import com.bee32.plover.arch.logging.ExceptionLog;

@Doc("站点监控")
public class SiteMonitorPage
        extends SiteTemplate {

    public SiteMonitorPage(Map<String, ?> _args) {
        super(_args);
    }

    @Override
    protected void _content()
            throws Exception {
        SiteManager manager = SiteManager.getInstance();
        table().border("1");
        {
            tr();
            th().rowspan("2").text("站点").end(); // 1-3
            th().rowspan("2").text("标题").end();
            th().rowspan("2").text("启动时间/次数").end();

            th().rowspan("2").text("启动耗时").end();// 4-6
            th().rowspan("2").text("运行时间").end();
            th().rowspan("2").text("运转率").end();
            th().colspan("4").text("服务时间").end(); // 7-18
            th().colspan("4").text("请求数").end();
            th().colspan("4").text("平均请求处理时间 (ms)").end();

            th().rowspan("2").text("异常数").end(); // 19
            end();
            tr();
            th().text("总").end();
            th().text("发起").end();
            th().text("微请求").end();
            th().text("其它").end();
            th().text("总").end();
            th().text("发起").end();
            th().text("微请求").end();
            th().text("其它").end();
            th().text("总").end();
            th().text("发起").end();
            th().text("微请求").end();
            th().text("其它").end();
            end();

            for (SiteInstance site : manager.getSites()) {
                String name = site.getName();
                String label = site.getLabel();
                SiteStats stats = site.getLocalStats();
                tr();
                td().text(name).end();
                td().text(label).end();
                td();
                {
                    Long startupTime = stats.getStartupTime();
                    if (startupTime != null)
                        text(Dates.sysDateTimeFormat.format(startupTime));
                    end();
                }

                dump(site, stats);

                td();
                {
                    ExceptionLog log = site.getAttribute(SiteElt.LOG_KEY);
                    if (log != null) {
                        a().href("exceptionView?site=" + name);
                        text(log.getEntries().size() + " exceptions");
                        end();
                    }
                    end();
                }
                end();
            }

            tr();
            td().colspan("19").style("background: gray").text("统计: ").end();
            end();

            for (SiteInstance site : manager.getSites()) {
                String name = site.getName();
                String label = site.getLabel();
                SiteStats allStats = site.getAllStats();
                tr();
                td().text(name).end();
                td().text(label).end();
                td().text("" + allStats.getGroups()).end();
                dump(site, allStats);
                td().text("" + allStats.getExceptions()).end();
                end();
            }
            end();
        }
    }

    static final NumberFormat meanTimeFormat;
    static final NumberFormat ratioFormat;
    static {
        meanTimeFormat = new DecimalFormat("0.0");
        ratioFormat = new DecimalFormat("0.0%");
    }

    static double utilizationScale = 5.0;

    public void dump(SiteInstance site, SiteStats stats) {

        long serviceTime = stats.getServiceTime();
        Long runTime = stats.getRunTime();
        String serviceRatioText;
        if (runTime != null) {
            double serviceRatio = (double) serviceTime / runTime; // * utilizationScale;
            // double spareRatio = 1 - serviceRatio;
            serviceRatioText = ratioFormat.format(100 * serviceRatio);
        } else {
            serviceRatioText = "";
        }

        long startupCost = stats.getStartupCost();
        durationField(startupCost);
        durationField(runTime);
        td().text(serviceRatioText).end();

        durationField(stats.getServiceTime());
        durationField(stats.getBee32ServiceTime());
        durationField(stats.getMicroServiceTime());
        durationField(stats.getOtherServiceTime());

        td().text("" + stats.getRequestCount()).end();
        td();
        {
            a().href("recentRequests?site=" + site.getName());
            text("" + stats.getBee32RequestCount());
            end(2);
        }
        td().text("" + stats.getMicroRequestCount()).end();
        td().text("" + stats.getOtherRequestCount()).end();

        meanTimeField(stats.getMeanServiceTime());
        meanTimeField(stats.getMeanBee32ServiceTime());
        meanTimeField(stats.getMeanMicroServiceTime());
        meanTimeField(stats.getMeanOtherServiceTime());
    }

    void durationField(Long duration) {
        td().text(DurationFormat.format(duration)).end();
    }

    void meanTimeField(float meanTime) {
        td();
        if (Float.isNaN(meanTime))
            text("");
        else
            text(meanTimeFormat.format(meanTime));
        end();
    }

}
