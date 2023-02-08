INSERT INTO food (food_no,
                  food_category_no,
                  food_nm,
                  brand,
                  weight,
                  weight_unit_cd,
                  per_serving_unit_cd,
                  nutrient_standards,
                  nutrient_standards_unit_cd,
                  writer,
                  total_score,
                  score_count,
                  row_stat_cd)
VALUES (1, 108, '닭가슴살큐브마늘맛', '맛있닭', 0, 'G', 'G', 0, 'G', '베이런', 12.5, 3, 'U');

insert into mb(mb_no,
               expert_cd,
               row_stat_cd)
values (1, 'PRO','C');

insert into mb(mb_no,
               row_stat_cd)
values (2, 'C');

insert into follow(follow_no,
                   mb_no,
                   follow_mb_no,
                   follow_date,
                   row_stat_cd)
values (1, 1, 2, '2022-10-24', 'C');

insert into score(score_no,
                  mb_no,
                  object_no,
                  score,
                  row_stat_cd)
values (1, 1, 1, 4, 'C');


insert into score(score_no,
                  mb_no,
                  object_no,
                  score,
                  row_stat_cd)
values (2, 2, 1, 5, 'C');

insert into score(score_no,
                  mb_no,
                  object_no,
                  score,
                  row_stat_cd)
values (3, 3, 1, 3.5, 'C');

insert into score(score_no,
                  mb_no,
                  object_no,
                  score,
                  row_stat_cd)
values (4, 1, 2, 2, 'C');