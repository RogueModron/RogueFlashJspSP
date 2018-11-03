package app.controllers.mappers;

import app.controllers.beans.DeckBean;
import app.model.entities.Deck;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class DeckMapperImpl implements DeckMapper {

    @Override
    public DeckBean deckToDeckBean(Deck d) {
        if ( d == null ) {
            return null;
        }

        DeckBean deckBean = new DeckBean();

        deckBean.setDeckId( d.getId() );
        deckBean.setDescription( d.getDescription() );
        deckBean.setNotes( d.getNotes() );

        return deckBean;
    }

    @Override
    public void updateDeckFromDeckBean(DeckBean s, Deck t) {
        if ( s == null ) {
            return;
        }

        t.setDescription( s.getDescription() );
        t.setNotes( s.getNotes() );
    }
}
