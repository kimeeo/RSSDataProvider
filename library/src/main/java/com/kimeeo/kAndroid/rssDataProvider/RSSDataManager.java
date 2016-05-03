package com.kimeeo.kAndroid.rssDataProvider;

import com.kimeeo.kAndroid.listViews.dataProvider.BackgroundNetworkDataProvider;
import com.kimeeo.kAndroid.listViews.dataProvider.DataModel;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;
import org.mcsoxford.rss.RSSReader;
import org.mcsoxford.rss.RSSReaderException;

import java.util.List;

/**
 * Created by BhavinPadhiyar on 03/05/16.
 */
abstract public class RSSDataManager extends BackgroundNetworkDataProvider
{

    public RSSDataManager()
    {
        setCanLoadRefresh(false);
        setRefreshEnabled(false);

    }

    @Override
    final protected int getMethod() {return 0;}

    @Override
    final protected Object getNextParam() {return null;}

    @Override
    final protected Class getDataModel() {
        return BaseDataModel.class;
    }

    @Override
    protected void invokeLoadNext() {
        String url = getNextURL();
        if (url != null) {
            if(url!=null) {
                try {
                    RSSReader reader = new RSSReader();
                    RSSFeed feed = reader.load(url);
                    BaseDataModel dataModel = new BaseDataModel();
                    dataModel.setFeed(feed);
                    processDataManager(dataModel);

                } catch (RSSReaderException e) {
                    setCanLoadNext(false);
                    dataLoadError(null);
                } catch (Exception e) {
                    setCanLoadNext(false);
                    dataLoadError(null);
                }
            }
            else
            {
                setCanLoadNext(false);
                dataLoadError(null);
            }
        } else {
            setCanLoadNext(false);
            dataLoadError(null);
        }
    }

    public class BaseDataModel implements DataModel
    {
        private List list;
        private RSSFeed feed;

        @Override
        public List getDataProvider() {
            return list;
        }
        @Override
        public void setDataProvider(List list) {
            this.list =list;
        }

        public void setFeed(RSSFeed feed) {
            this.feed = feed;
            List<RSSItem> list = feed.getItems();
            setDataProvider(list);
        }

        public RSSFeed getFeed() {
            return feed;
        }
    };
    @Override
    final protected void invokeLoadRefresh() {
        setCanLoadRefresh(false);
        setRefreshEnabled(false);
    }
}

