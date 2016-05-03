package com.kimeeo.kAndroid.rssDataProvider;

import com.kimeeo.kAndroid.listViews.dataProvider.BackgroundDataProvider;
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
    RSSReader reader;
    public RSSDataManager()
    {
        setCanLoadRefresh(false);
        setRefreshEnabled(false);
        reader = new RSSReader();
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
        if(getNextURL()!=null)
        {
            String url = getNextURL();
            try {
                RSSFeed feed= reader.load(url);
                List<RSSItem> data=feed.getItems();
                BaseDataModel dataModel = new BaseDataModel();
                dataModel.setDataProvider(data);
                processDataManager(dataModel);

            } catch (RSSReaderException e) {
                setCanLoadNext(false);
                dataLoadError(null);
            }
        }
        else {
            setCanLoadNext(false);
            dataLoadError(null);
        }
    }

    public class BaseDataModel implements DataModel
    {
        private List list;
        @Override
        public List getDataProvider() {
            return list;
        }
        @Override
        public void setDataProvider(List list) {
            this.list =list;
        }
    };
    @Override
    final protected void invokeLoadRefresh() {
        setCanLoadRefresh(false);
        setRefreshEnabled(false);
    }
}
