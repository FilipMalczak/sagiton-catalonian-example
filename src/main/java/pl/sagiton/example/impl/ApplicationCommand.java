package pl.sagiton.example.impl;

import pl.sagiton.example.impl.model.Visitation;
import pl.sagiton.example.impl.processing.SimpleVisitationHandler;
import pl.sagiton.example.impl.processing.VisitationHandler;

import static pl.sagiton.example.impl.utils.PredicateUtils.isEqual;

public enum ApplicationCommand {

    CITY {
        @Override
        public VisitationHandler getHandler(String arg) {
            return new SimpleVisitationHandler(isEqual(Visitation::getCity, arg), v -> v.getName()+","+v.getId());
        }
    }, ID {
        @Override
        public VisitationHandler getHandler(String arg) {
            return new SimpleVisitationHandler(isEqual(Visitation::getId, arg), Visitation::getCity);
        }
    };

    public abstract VisitationHandler getHandler(String arg);
}
