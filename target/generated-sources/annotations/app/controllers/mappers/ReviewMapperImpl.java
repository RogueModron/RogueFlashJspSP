package app.controllers.mappers;

import app.controllers.beans.ReviewBean;
import app.model.entities.Card;
import app.model.entities.CardInstance;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewBean cardAndInstanceToReviewBean(Card c, CardInstance i) {
        if ( c == null && i == null ) {
            return null;
        }

        ReviewBean reviewBean = new ReviewBean();

        if ( c != null ) {
            reviewBean.setCardId( c.getId() );
            reviewBean.setSideA( c.getSideA() );
            reviewBean.setSideB( c.getSideB() );
            reviewBean.setNotes( c.getNotes() );
            reviewBean.setTags( c.getTags() );
        }
        if ( i != null ) {
            reviewBean.setCardInstanceId( i.getId() );
            reviewBean.setSideAToB( i.isSideAToB() );
        }

        return reviewBean;
    }
}
