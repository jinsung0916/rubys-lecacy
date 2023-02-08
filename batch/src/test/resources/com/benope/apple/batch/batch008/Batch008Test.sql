INSERT INTO category (category_no,
                      category_type_cd,
                      parent_category_no,
                      category_nm,
                      row_stat_cd)
VALUES (101, 'RANKING', 0, '전체랭킹', 'C');

INSERT INTO category (category_no,
                      category_type_cd,
                      parent_category_no,
                      category_nm,
                      row_stat_cd)
VALUES (102, 'RANKING', 0, '닭가슴살', 'C');

INSERT INTO category (category_no,
                      category_type_cd,
                      parent_category_no,
                      category_nm,
                      row_stat_cd)
VALUES (103, 'RANKING', 0, '가공육', 'C');

INSERT INTO category_ranking_condition (category_no,
                                        category_ranking_condition_ord,
                                        ranking_condition_type_cd,
                                        food_category_no)
VALUES (101, 1, 'ALL', null);

INSERT INTO category_ranking_condition (category_no,
                                        category_ranking_condition_ord,
                                        ranking_condition_type_cd,
                                        food_category_no)
VALUES (102, 1, 'MAIN', 1);

INSERT INTO category_ranking_condition (category_no,
                                        category_ranking_condition_ord,
                                        ranking_condition_type_cd,
                                        food_category_no)
VALUES (103, 1, 'SUB', 4);

INSERT INTO category_ranking_condition (category_no,
                                        category_ranking_condition_ord,
                                        ranking_condition_type_cd,
                                        food_category_no)
VALUES (103, 2, 'SUB', 5);

insert into category(category_no,
                     category_type_cd,
                     parent_category_no,
                     row_stat_cd)
values (2, 'FOOD', 1, 'C');

insert into category(category_no,
                     category_type_cd,
                     parent_category_no,
                     row_stat_cd)
values (3, 'FOOD', 1, 'C');

insert into food(food_no,
                 food_category_no,
                 row_stat_cd)
values (1, 2, 'C');

insert into food(food_no,
                 food_category_no,
                 row_stat_cd)
values (2, 3, 'C');

insert into food(food_no,
                 food_category_no,
                 row_stat_cd)
values (3, 4, 'C');

insert into food(food_no,
                 food_category_no,
                 row_stat_cd)
values (4, 5, 'C');

insert into food(food_no,
                 food_category_no,
                 row_stat_cd)
values (5, 6, 'C');

insert into score(score_no,
                  mb_no,
                  object_no,
                  score,
                  updated_at,
                  row_stat_cd)
values (1, 1, 1, 4.0, '2022-11-26 10:00:00', 'C');

insert into score(score_no,
                  mb_no,
                  object_no,
                  score,
                  updated_at,
                  row_stat_cd)
values (2, 2, 2, 4.1, '2022-11-26 10:00:00', 'C');

insert into score(score_no,
                  mb_no,
                  object_no,
                  score,
                  updated_at,
                  row_stat_cd)
values (3, 3, 3, 4.3, '2022-11-26 10:00:00', 'C');

insert into score(score_no,
                  mb_no,
                  object_no,
                  score,
                  updated_at,
                  row_stat_cd)
values (4, 4, 4, 4.4, '2022-11-26 10:00:00', 'C');

insert into score(score_no,
                  mb_no,
                  object_no,
                  score,
                  updated_at,
                  row_stat_cd)
values (5, 5, 5, 4.5, '2022-11-26 10:00:00', 'C');