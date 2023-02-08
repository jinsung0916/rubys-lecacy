INSERT INTO food (food_no,
                  food_category_no,
                  food_nm,
                  brand,
                  manufacturer,
                  carbohydrate,
                  sugars,
                  sugar_alcohol,
                  dietary_fiber,
                  allulose,
                  erythritol,
                  protein,
                  fat,
                  saturated_fat,
                  trans_fat,
                  nateulyum,
                  cholesterol,
                  calories,
                  weight,
                  weight_unit_cd,
                  per_serving,
                  per_serving_unit_cd,
                  nutrient_standards,
                  nutrient_standards_unit_cd,
                  product_status_cd,
                  storage_type,
                  front_image_no,
                  writer,
                  tag_count,
                  row_stat_cd)
VALUES (1, 108, '닭가슴살스테이크오리지널', '맛있닭', '유한맛있닭', 8.00, 1.00, null, null, null, null, 22.00, 2.20, 0.60, 0.00, 294.00,
        20.00, 140.00, 100.00, 'G', 100.00, 'G', 100.00, 'G', 'FOR_SALE', null, null, '베이런', null, 'C');

INSERT INTO food (food_no,
                  row_stat_cd)
VALUES (2, 'D');

INSERT INTO category (category_no,
                      category_type_cd,
                      parent_category_no,
                      category_nm,
                      row_stat_cd)
VALUES (10, 'FOOD', 0, '육가공류', 'C');


INSERT INTO category (category_no,
                      category_type_cd,
                      parent_category_no,
                      category_nm,
                      row_stat_cd)
VALUES (108, 'FOOD', 10, '닭가슴살류', 'C');

