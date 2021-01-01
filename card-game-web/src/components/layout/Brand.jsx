import React from "react";
import { useDispatch } from "react-redux";
import { clickedGoHome } from "../../store/lobby/lobby-store";
import * as S from "./styles/Header.styles";

const Brand = () => {
  const dispatch = useDispatch();
  return (
    <S.Brand onClick={() => dispatch(clickedGoHome())}>Revelation 21:8</S.Brand>
  );
};

export default Brand;
