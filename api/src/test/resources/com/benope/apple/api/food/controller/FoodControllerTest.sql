insert into mb (mb_no,
                row_stat_cd)
values (1, 'C');

insert into food (food_no,
                  food_category_no,
                  food_nm,
                  brand,
                  calories,
                  nateulyum,
                  carbohydrate,
                  sugars,
                  protein,
                  fat,
                  saturated_fat,
                  trans_fat,
                  cholesterol,
                  dietary_fiber,
                  allulose,
                  erythritol,
                  sugar_alcohol,
                  nutrient_standards,
                  nutrient_standards_unit_cd,
                  row_stat_cd,
                  rand_num)
values (1, 1, '닭가슴살스테이크오리지널', '맛있닭', 280, 400, 33, 15, 20, 8, 4, 0, 0, 1, 1, 1, 1, 70, 'G', 'C', -1278718274);

insert into food (food_no,
                  food_category_no,
                  food_nm,
                  brand,
                  calories,
                  nateulyum,
                  carbohydrate,
                  sugars,
                  protein,
                  fat,
                  saturated_fat,
                  trans_fat,
                  cholesterol,
                  dietary_fiber,
                  allulose,
                  erythritol,
                  sugar_alcohol,
                  nutrient_standards,
                  nutrient_standards_unit_cd,
                  row_stat_cd,
                  rand_num)
values (2, 1, '닭가슴살스테이크오리지널', '맛있닭', 280, 400, 33, 15, 20, 8, 4, 0, 0, 1, 1, 1, 1, 70, 'G', 'C', 1800503241);

insert into score(score_no,
                  mb_no,
                  object_no,
                  score,
                  row_stat_cd)
values (1, 1,  1, 5.0, 'C');

insert into score(score_no,
                  mb_no,
                  object_no,
                  score,
                  row_stat_cd)
values (2, 1, 2, 4.0, 'C');