create type status as enum ('active', 'inactive');
create type member_provider as enum ('kakao','google','naver','crewstation');
create type member_role as enum ('member','admin');
create type gender as enum ('male','female');
create type read_status as enum ('read','unread');
create type crew_role as enum ('leader','partner');
create type delivery_method as enum ('direct','parcel');
create type accompany_status as enum ('long','short');
create type secret as enum ('private','public');
create type phase as enum ('request','pending','refund','success');
create type image_type as enum ('main','sub');
create type process_status as enum ('pending','reject','resolved');

ALTER TYPE phase ADD VALUE 'received';
ALTER TYPE phase ADD VALUE 'reviewed';