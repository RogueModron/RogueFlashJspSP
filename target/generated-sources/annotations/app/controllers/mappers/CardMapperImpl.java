package app.controllers.mappers;

import app.controllers.beans.CardBean;
import app.model.entities.Card;
import app.model.entities.CardInstance;
import app.model.entities.Deck;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class CardMapperImpl implements CardMapper {

    @Override
    public CardBean cardAndInstanceToCardBean(Card c, CardInstance i) {
        if ( c == null && i == null ) {
            return null;
        }

        CardBean cardBean = new CardBean();

        if ( c != null ) {
            int id = cDeckId( c );
            cardBean.setDeckId( id );
            cardBean.setCardId( c.getId() );
            cardBean.setSideA( c.getSideA() );
            cardBean.setSideB( c.getSideB() );
            cardBean.setNotes( c.getNotes() );
            cardBean.setTags( c.getTags() );
        }
        cardBean.setSideBToA( !i.isDisabled() );

        return cardBean;
    }

    private int cDeckId(Card card) {
        if ( card == null ) {
            return 0;
        }
        Deck deck = card.getDeck();
        if ( deck == null ) {
            return 0;
        }
        int id = deck.getId();
        return id;
    }
}
