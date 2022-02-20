import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
const Error = () => {
  const navigate = useNavigate();

  useEffect(async () => {
    await new Promise((resolve) => setTimeout(resolve, 1000));
    navigate("/");
  }, []);

  return <div>잘못된 경로입니다. 잠시 후 메인 화면으로 이동합니다.</div>;
};

export default Error;
