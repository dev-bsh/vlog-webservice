import React, { useEffect } from "react";
import Header from "../common/Header";

import SideNav from "../common/SideNav";
import Content from "../main/Content";
import Sort from "../main/Sort";
import MainWrapper from "../styled/commonStyled/MainWrapper";
import ContentWrapper from "../styled/mainStyled/ContentWrapper";
import HomeWrapper from "../styled/mainStyled/HomeWrapper";
import Upload from "../upload/Upload";

import { Route, Routes, Link } from "react-router-dom";
import { useMediaQuery } from "react-responsive";

const Main = () => {
  const isMobile = useMediaQuery({
    query: "(max-width:1024px)",
  });
  return (
    <HomeWrapper>
      {isMobile ? null : <SideNav />}
      <MainWrapper>
        <ContentWrapper>
          <Sort />
          <Content />
        </ContentWrapper>
      </MainWrapper>
    </HomeWrapper>
  );
};

export default Main;
