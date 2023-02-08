INSERT INTO feed (feed_no,
                  mb_no,
                  feed_type_cd,
                  view_cnt,
                  comment_cnt,
                  like_cnt,
                  scrap_cnt,
                  created_at,
                  created_by,
                  row_stat_cd)
VALUES (1, 2, 'FEED', 5, 0, 0, 1, '2022-01-01', 2, 'C');

INSERT INTO theme (theme_no,
                   theme_nm,
                   display_nm,
                   reorder_no,
                   row_stat_cd)
VALUES (1, '테마', '전시명', 1, 'C');

INSERT INTO theme_feed (theme_feed_no,
                        theme_no,
                        feed_no,
                        reorder_no,
                        row_stat_cd)
values (1, 1, 1, 1, 'C');

INSERT INTO theme_feed (theme_feed_no,
                        theme_no,
                        feed_no,
                        reorder_no,
                        row_stat_cd)
values (2, 1, 2, 2, 'D');

INSERT INTO feed (feed_no,
                  mb_no,
                  feed_type_cd,
                  view_cnt,
                  comment_cnt,
                  like_cnt,
                  scrap_cnt,
                  created_at,
                  created_by,
                  row_stat_cd)
VALUES (2, 2, 'FEED', 0, 1, 0, 0, '2022-01-01', 2, 'C');

insert into comment(object_no,
                    comment_type_cd,
                    mb_no,
                    row_stat_cd)
values (2, 'FEED', 1, 'C');

INSERT INTO feed (feed_no,
                  mb_no,
                  feed_type_cd,
                  view_cnt,
                  comment_cnt,
                  like_cnt,
                  scrap_cnt,
                  created_at,
                  created_by,
                  row_stat_cd)
VALUES (3, 2, 'FEED', 0, 0, 1, 1, '2022-01-01', 2, 'C');

INSERT INTO feed_detail (feed_no,
                         feed_detail_ord,
                         feed_detail_title,
                         feed_detail_content,
                         upload_img_food_relation_1)
VALUES (3, 1, '피드댓글테스트입니다.베노프짱짱맨', '베노프에서 1',
        '[
          {
            "food_no": 1,
            "user_input": 100
          }
        ]' FORMAT JSON);

INSERT INTO feed_detail (feed_no,
                         feed_detail_ord,
                         feed_detail_title,
                         feed_detail_content,
                         upload_img_food_relation_1)
VALUES (3, 2, '피드댓글테스트입니다.베노프짱짱맨', '베노프에서 1',
        '[
          {
            "food_no": 2,
            "user_input": 200
          }
        ]' FORMAT JSON);

insert into reaction(mb_no,
                     object_no,
                     reaction_type_cd,
                     reaction_target_cd,
                     row_stat_cd)
values (1, 3, 'LIKE', 'FEED', 'C');

insert into scrap(scrap_type_cd, mb_no, feed_no, row_stat_cd)
values ('SCRAP', 1, 3, 'C');