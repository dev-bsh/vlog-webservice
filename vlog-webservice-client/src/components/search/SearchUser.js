import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getUserSearch } from "../../redux/module/search";
import SearchUserStyled from "../styled/searchStyled/SearchUserStyled";
import UserBlock from "../common/UserBlock";
import SearchResultWrapper from "../styled//searchStyled/SearchResultWrapper";
import { Link } from "react-router-dom";

const SearchUser = () => {
  const dispatch = useDispatch();
  const keyword = useSelector((state) => state.search.input);
  const userNameList = useSelector((state) => state.search.userResults);

  const userList = userNameList.map((user, index) => (
    <Link to={`/channel/${user.name}/${user.userId}`} key={index}>
      <UserBlock userName={user.name} src={user.picture} />
    </Link>
  ));
  return (
    <SearchUserStyled>
      Users
      <SearchResultWrapper>{userList}</SearchResultWrapper>
    </SearchUserStyled>
  );
};

export default SearchUser;
