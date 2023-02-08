insert into mb (mb_no,
                row_stat_cd)
values (1, 'C');

insert into mb (mb_no,
                nickname,
                profile_image_no,
                row_stat_cd)
values (2, 'example', 1, 'C');

INSERT INTO feed (feed_no,
                  mb_no,
                  feed_type_cd,
                  view_cnt,
                  comment_cnt,
                  like_cnt,
                  scrap_cnt,
                  created_by,
                  row_stat_cd)
VALUES (1, 2, 'FEED', 5, 0, 0, 1, 2, 'C');


INSERT INTO feed (feed_no,
                  mb_no,
                  feed_type_cd,
                  view_cnt,
                  comment_cnt,
                  like_cnt,
                  scrap_cnt,
                  created_by,
                  row_stat_cd)
VALUES (2, 2, 'FEED', 0, 1, 0, 0, 2, 'C');

insert into comment(object_no, comment_type_cd, mb_no, row_stat_cd)
values (2, 'FEED', 1, 'C');

INSERT INTO feed (feed_no,
                  mb_no,
                  feed_type_cd,
                  view_cnt,
                  comment_cnt,
                  like_cnt,
                  scrap_cnt,
                  created_by,
                  row_stat_cd)
VALUES (3, 2, 'FEED', 0, 0, 1, 1, 2, 'C');

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
          },
          {
            "food_no": 2,
            "user_input": 100
          },
          {
            "food_no": 3,
            "user_input": 100
          }
        ]' FORMAT JSON);

INSERT INTO feed_detail (feed_no,
                         feed_detail_ord,
                         feed_detail_title,
                         feed_detail_content)
VALUES (3, 2, '피드댓글테스트입니다.베노프짱짱맨', '베노프에서 1');

INSERT INTO feed_detail (feed_no,
                         feed_detail_ord,
                         feed_detail_title,
                         feed_detail_content)
VALUES (3, 3, '피드댓글테스트입니다.베노프짱짱맨', '베노프에서 1');

insert into food(food_no,
                 food_category_no,
                 food_nm,
                 brand,
                 protein,
                 fat,
                 saturated_fat,
                 calories,
                 weight,
                 weight_unit_cd,
                 per_serving_unit_cd,
                 nutrient_standards,
                 nutrient_standards_unit_cd,
                 writer,
                 tag_count,
                 row_stat_cd)
values (1, 0, '20bar 초콜릿카라멜', '베노프', 100, 100, 100, 100, 100, 'G', 'G', 200, 'G', 'AJ', 1, 'C');

insert into food(food_no,
                 food_category_no,
                 food_nm,
                 brand,
                 protein,
                 fat,
                 saturated_fat,
                 calories,
                 weight,
                 weight_unit_cd,
                 per_serving_unit_cd,
                 nutrient_standards,
                 nutrient_standards_unit_cd,
                 writer,
                 tag_count,
                 row_stat_cd)
values (2, 0, '20bar 인절미', '베노프', 100, 100, 100, 100, 100, 'L', 'L', 200, 'L', 'AJ', 1, 'C');

insert into food_tag (food_tag_no,
                      food_no,
                      mb_no,
                      food_tag_target_cd,
                      object_no,
                      row_stat_cd)
values (1, 1, 2, 'FEED', 3, 'C');

insert into food_tag (food_tag_no,
                      food_no,
                      mb_no,
                      food_tag_target_cd,
                      object_no,
                      row_stat_cd)
values (2, 2, 2, 'FEED', 3, 'C');