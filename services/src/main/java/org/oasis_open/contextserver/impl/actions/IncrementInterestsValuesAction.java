package org.oasis_open.contextserver.impl.actions;

import org.apache.commons.beanutils.PropertyUtils;
import org.oasis_open.contextserver.api.Event;
import org.oasis_open.contextserver.api.actions.Action;
import org.oasis_open.contextserver.api.actions.ActionExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by toto on 29/08/14.
 */
public class IncrementInterestsValuesAction implements ActionExecutor {

    @Override
    public boolean execute(Action action, Event event) {
        boolean modified = false;
        Map<String, Object> profileProps = event.getProfile().getProperties();

        try {
            Map<String, Object> interests = (Map<String, Object>) PropertyUtils.getProperty(event, "target.properties.interests");
            if (interests != null) {
                for (Map.Entry<String, Object> s : interests.entrySet()) {
                    int value = (Integer) s.getValue();

                    HashMap<String, Object> profileInterests = (HashMap<String, Object>) event.getProfile().getProperty("interests");
                    if(profileInterests != null){
                        profileInterests = new HashMap<String, Object>(profileInterests);
                        int oldValue = (profileInterests.containsKey(s.getKey())) ? (Integer) profileInterests.get(s.getKey()) : 0;
                        profileInterests.put(s.getKey(), value + oldValue);
                    }else {
                        profileInterests = new HashMap<String, Object>();
                        profileInterests.put(s.getKey(), value);
                    }
                    event.getProfile().setProperty("interests", profileInterests);
                    modified = true;
                }
            }
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }

        return modified;
    }
}