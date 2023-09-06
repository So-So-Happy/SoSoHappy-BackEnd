package sosohappy.feedservice.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sosohappy.feedservice.domain.dto.FeedDto;
import sosohappy.feedservice.domain.dto.HappinessDto;
import sosohappy.feedservice.domain.dto.NicknameAndDateDto;
import sosohappy.feedservice.domain.entity.Feed;

import java.util.List;
import java.util.Optional;

import static sosohappy.feedservice.domain.entity.QFeed.*;

@Repository
@RequiredArgsConstructor
public class FeedQueryRepositoryImpl implements FeedQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FeedDto> findMonthFeedDtoByNicknameAndDateDto(NicknameAndDateDto nicknameAndDateDto) {
        return queryFactory
                .select(Projections.constructor(
                        FeedDto.class,
                        feed
                ))
                .from(feed)
                .where(
                        nickNameEq(nicknameAndDateDto.getNickname()),
                        monthEq(nicknameAndDateDto.getDate())
                )
                .orderBy(feed.date.asc())
                .fetch();
    }

    @Override
    public Optional<FeedDto> findDayFeedDtoByNicknameAndDateDto(NicknameAndDateDto nicknameAndDateDto) {
        return Optional.ofNullable(
                queryFactory
                        .select(Projections.constructor(
                                FeedDto.class,
                                feed
                        ))
                        .from(feed)
                        .where(
                                nickNameEq(nicknameAndDateDto.getNickname()),
                                dayEq(nicknameAndDateDto.getDate())
                        )
                        .fetchOne()
        );
    }

    @Override
    public Optional<Feed> findByNicknameAndDate(String nickname, Long date) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(feed)
                        .where(
                                nickNameEq(nickname),
                                dayEq(date)
                        )
                        .fetchOne()
        );
    }

    @Override
    public List<HappinessDto> findMonthHappinessDtoByNicknameAndDateDto(NicknameAndDateDto nicknameAndDateDto) {
        return queryFactory
                .select(Projections.constructor(
                        HappinessDto.class,
                        feed
                ))
                .from(feed)
                .where(
                        nickNameEq(nicknameAndDateDto.getNickname()),
                        monthEq(nicknameAndDateDto.getDate())
                )
                .fetch();
    }

    // ----------------------------------------------------------------- //

    private BooleanExpression nickNameEq(String nickname){
        return feed.nickname.eq(nickname);
    }

    private BooleanExpression monthEq(Long date) {
        return feed.date.divide(10000000000L).floor().eq(date / 10000000000L);
    }

    private BooleanExpression dayEq(Long date){
        return feed.date.divide(100000000L).floor().eq(date / 100000000L);
    }
}
