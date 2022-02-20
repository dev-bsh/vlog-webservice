import React, { useEffect, useState } from "react";
import Header from "../common/Header";
import UploadButton from "../common/UploadButton";
import SideNav from "../common/SideNav";

import MainWrapper from "../styled/commonStyled/MainWrapper";
import ContentWrapper from "../styled/mainStyled/ContentWrapper";

import SearchResultWrapper from "../styled/searchStyled/SearchResultWrapper";

import HomeWrapper from "../styled/mainStyled/HomeWrapper";
import Upload from "../upload/Upload";
import { Route, Routes, NavLink } from "react-router-dom";
import SearchUser from "./SearchUser";
import SearchTag from "./SearchTag";
import Content from "../main/Content";
import { useSelector, useDispatch } from "react-redux";
import { getSearch, setInput } from "../../redux/module/search";
import SortStyled from "../styled/mainStyled/SortStyled";
import { useMediaQuery } from "react-responsive";

const navLinkStyled = ({ isActive }) => {
  return {
    borderBottom: isActive ? "2px solid black" : "white",
  };
};
const SearchResult = () => {
  const dispatch = useDispatch();
  const keyword = useSelector((state) => state.search.input);
  const isEmpty = useSelector((state) => state.search.isEmpty);
  const results = useSelector((state) => state.search.results);
  const isMobile = useMediaQuery({
    query: "(max-width:1024px)",
  });

  useEffect(async () => {
    await dispatch(getSearch(keyword));
    document.body.style.overflow = "auto";
  }, []);

  return (
    <HomeWrapper>
      {isMobile ? null : <SideNav />}
      <SearchResultWrapper>
        {isEmpty ? (
          <p>"{keyword}"에 대한 검색결과가 없습니다.</p>
        ) : (
          <>
            <p>"{keyword}"에 대한 검색결과입니다.</p>
            <p>{results.length}개의 콘텐츠가 있습니다.</p>
          </>
        )}
        <ContentWrapper>
          <SearchUser />
          <SearchTag />
        </ContentWrapper>
      </SearchResultWrapper>
    </HomeWrapper>
  );
};

export default SearchResult;
