-- product_discount, activated 컬럼 추가
alter table product_discount add column activated bit(1) after discount_date_to;

-- product_store, name 컬럼 제거
alter table product_store drop column name;

-- product_category, type 컬럼 제거
alter table product_category drop column `type`;
