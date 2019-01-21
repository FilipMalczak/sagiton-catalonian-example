package pl.sagiton.example;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import static pl.sagiton.example.PredicateUtils.isEqual;

public enum ApplicationCommand {

    CITY {
        @Override
        VisitationHandler getHandler(String arg) {
            return new SimpleVisitationHandler(isEqual(Visitation::getCity, arg), v -> v.getName()+","+v.getId());
        }
    }, ID {
        @Override
        VisitationHandler getHandler(String arg) {
            return new SimpleVisitationHandler(isEqual(Visitation::getId, arg), Visitation::getCity);
        }
    };

    abstract VisitationHandler getHandler(String arg);
}
