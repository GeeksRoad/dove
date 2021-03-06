package zg.dove.filter;

import java.util.ArrayList;
import java.util.List;

public class DupFilter implements IFilter<Object, Object> {
    private List<IFilter<Object, Object>> filters = new ArrayList();

    public boolean add(IFilter filter) {
        return filters.add(filter);
    }

    public boolean remove(IFilter filter) {
        return filters.remove(filter);
    }

    @Override
    public Object onFilterIn(Object context, Object msg) throws Exception {
        for (IFilter<Object, Object> filter : filters) {
            msg = filter.onFilterIn(context, msg);
            if (msg == null) {
                break;
            }
        }
        return msg;
    }

    @Override
    public Object onFilterOut(Object context, Object msg) throws Exception {
        for (int i = filters.size() - 1; i >= 0; i--) {
            IFilter<Object, Object> filter = filters.get(i);
            msg = filter.onFilterOut(context, msg);
            if (msg == null) {
                break;
            }
        }
        return msg;
    }

    @Override
    public Throwable onFilterException(Object context, Throwable t) {
        for (int i = filters.size() - 1; i >= 0; i--) {
            IFilter<Object, Object> filter = filters.get(i);
            t = filter.onFilterException(context, t);
            if (t == null) {
                break;
            }
        }
        return t;
    }
}
