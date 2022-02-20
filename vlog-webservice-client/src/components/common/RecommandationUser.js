import React, { useState } from "react";
import RecommandationContentStyled from "../styled/commonStyled/RecommandationContentStyled";
import UserBlock from "./UserBlock";
import { Link } from "react-router-dom";
import RecommandTextWrapper from "../styled/commonStyled/RecommandTextWrapper";

const RecommandationUser = () => {
  const userNameList = ["철수", "짱구", "훈이"];

  const userList = userNameList.map((user, index) => (
    <Link to={`/channel/${user}`} key={index}>
      <UserBlock key={index} userName={user} />
    </Link>
  ));

  return (
    <>
      <RecommandTextWrapper>Recommnad User</RecommandTextWrapper>
      <RecommandationContentStyled>{userList}</RecommandationContentStyled>
    </>
  );
};

export default RecommandationUser;
