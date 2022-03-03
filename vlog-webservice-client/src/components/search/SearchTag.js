import React, { useEffect, useState } from "react";
import SearchTagStyled from "../styled/searchStyled/SearchTagStyled";
import Content from "../main/Content";
import { useDispatch, useSelector } from "react-redux";
import { getSearch } from "../../redux/module/search";
import PostWrapperStyled from "../styled/mainStyled/PostWrapperStyled";
import Post from "../main/Post";

const SearchTag = () => {
  const keyword = useSelector((state) => state.search.input);
  const results = useSelector((state) => state.search.results);
  const dispatch = useDispatch();

  const resultList = results.map((post, index) => (
    <Post key={index} post={post}></Post>
  ));

  return (
    <SearchTagStyled>
      Posts
      <PostWrapperStyled>{resultList}</PostWrapperStyled>
    </SearchTagStyled>
  );
};

export default SearchTag;
