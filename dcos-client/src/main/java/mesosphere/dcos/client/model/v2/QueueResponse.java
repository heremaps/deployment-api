package mesosphere.dcos.client.model.v2;

import java.util.Collection;
import mesosphere.dcos.client.utils.ModelUtils;

public class QueueResponse {

    private Collection<QueueElement> queue;

    public Collection<QueueElement> getQueue() {
        return queue;
    }

    public void setQueue(Collection<QueueElement> queue) {
        this.queue = queue;
    }

    @Override
    public String toString() {
        return ModelUtils.toString(this);
    }
}
