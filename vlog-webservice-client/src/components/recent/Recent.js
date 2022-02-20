import React from "react";
import Header from "../common/Header";

import SideNav from "../common/SideNav";
import RecentContent from "./RecentContent";
import Sort from "../main/Sort";
import MainWrapper from "../styled/commonStyled/MainWrapper";
import ContentWrapper from "../styled/mainStyled/ContentWrapper";
import HomeWrapper from "../styled/mainStyled/HomeWrapper";

import { Route, Routes, Link } from "react-router-dom";
import { useMediaQuery } from "react-responsive";
const Recent = () => {
  const isMobile = useMediaQuery({
    query: "(max-width:1024px)",
  });
  return (
    <HomeWrapper>
      {isMobile ? null : <SideNav />}
      <MainWrapper>
        <ContentWrapper>
          <Sort />
          <RecentContent />
        </ContentWrapper>
      </MainWrapper>
    </HomeWrapper>
  );
};

export default Recent;
