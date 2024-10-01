package auxiliaries.fileData;

import auxiliaries.queues.QueueLink;

public class LinkDataWrapper {
    public final int FROM_INDEX;
    public final QueueLink LINK;

    public LinkDataWrapper(int fromIndex, QueueLink link) {
        FROM_INDEX = fromIndex;
        LINK = link;
    }    
}
