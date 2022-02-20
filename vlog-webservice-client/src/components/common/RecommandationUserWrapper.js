import React from "react";
import RecommandationWrapper from "../styled/commonStyled/RecommandationWrapper";
import RecommandTextWrapper from "../styled/commonStyled/RecommandTextWrapper";
import RecommandationUser from "./RecommandationUser";

const RecommandationUserWrapper = () => {
  return (
    <RecommandationWrapper>
      <RecommandTextWrapper>123</RecommandTextWrapper>
      <RecommandationUser />
    </RecommandationWrapper>
  );
};

export default RecommandationUserWrapper;
