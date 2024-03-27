"use client";
import React, { ReactNode, useEffect, useState } from "react";
import ReactDOM from "react-dom";

interface ModalProps {
  open: boolean;
  children: ReactNode;
}

export default function Modal({ open, children }: ModalProps) {
  const [prevScrollY, setPrevScrollY] = useState<number | undefined>(undefined);
  const [hasScrollbar, setHasScrollbar] = useState<boolean>(false);

  // 스크롤이 있는지 확인하는 함수
  const checkScrollbar = (): void => {
    setHasScrollbar(document.body.scrollHeight > window.innerHeight);
  };

  // 스크롤을 방지하고 현재 위치를 반환
  const preventScroll = (): void => {
    const currentScrollY = window.scrollY;
    document.body.style.position = "fixed";
    document.body.style.width = "100%";
    document.body.style.top = `-${currentScrollY}px`; // 현재 스크롤 위치
    document.body.style.overflowY = hasScrollbar ? "scroll" : "hidden";
    setPrevScrollY(currentScrollY);
  };

  // 스크롤을 허용하고, 스크롤 방지 함수에서 반환된 위치로 이동
  const allowScroll = (): void => {
    document.body.style.position = "";
    document.body.style.width = "";
    document.body.style.top = "";
    document.body.style.overflowY = "";
    if (prevScrollY !== undefined) {
      window.scrollTo(0, prevScrollY);
    }
  };

  useEffect((): void => {
    // 컴포넌트가 마운트될 때 스크롤바 여부를 확인
    checkScrollbar();

    if (open) {
      checkScrollbar();
      preventScroll();
    } else {
      allowScroll();
    }
  }, []);

  useEffect((): void => {
    // 모달이 열릴 때마다 스크롤바 여부를 확인하고, 스크롤 방지/허용 함수 호출
    if (open) {
      checkScrollbar();
      preventScroll();
    } else {
      allowScroll();
    }
  }, [open]);

  if (!open) return null;

  return ReactDOM.createPortal(
    <>
      <div className="fixed top-0 bottom-0 left-0 right-0 z-50 bg-black bg-opacity-40" />
      <div className="fixed z-50 px-20 py-12 transform -translate-x-1/2 -translate-y-1/2 bg-white top-1/2 left-1/2 rounded-xl mb:px-8 mb:py-8 mb:w-[80%]">
        {children}
      </div>
    </>,
    document.getElementById("globalModal") as HTMLElement
  );
}
