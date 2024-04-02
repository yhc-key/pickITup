"use client";
import { useState, useRef, useEffect } from "react";
import { useMediaQuery } from "react-responsive";

import Image from "next/image";
import Link from "next/link";
import { Noto_Sans_KR } from "next/font/google";

import Dots from "@/components/onBoarding/dots";
import Page1 from "@/components/onBoarding/page1";
import Page2 from "@/components/onBoarding/page2";
import Page3 from "@/components/onBoarding/page3";
import Page4 from "@/components/onBoarding/page4";
import Page5 from "@/components/onBoarding/page5";

const noto = Noto_Sans_KR({
  subsets: ["latin"], // 또는 preload: false
});

export default function Home() {
  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });

  const [scrollIdx, setScrollIdx] = useState<number>(1);
  const mainWrapperRef = useRef<HTMLDivElement>(null);
  const laptopImageRef = useRef<HTMLImageElement>(null);
  const DIVIDER_HEIGHT = 4;

  useEffect(() => {
    let isScrolling = false;
    const wheelHandler = (e: WheelEvent) => {
      e.preventDefault();
      if (isScrolling) return;

      isScrolling = true;

      const { deltaY } = e;
      const { scrollTop } = mainWrapperRef.current!;
      const pageHeight = window.innerHeight;

      if (deltaY > 0) {
        // 스크롤 내릴 때
        if (scrollTop >= 0 && scrollTop < pageHeight) {
          // 현재 1페이지
          mainWrapperRef.current?.scrollTo({
            top: pageHeight + DIVIDER_HEIGHT,
            left: 0,
            behavior: "smooth",
          });
          setScrollIdx(2);

          if (laptopImageRef.current) {
            laptopImageRef.current.style.left = "70%";
            laptopImageRef.current.style.transform =
              "translate(-50%, 18%) scale(0.4)";
            laptopImageRef.current.style.bottom = "10%";
          }
        } else if (scrollTop >= pageHeight && scrollTop < pageHeight * 2) {
          // 현재 2페이지
          mainWrapperRef.current?.scrollTo({
            top: scrollTop + pageHeight + DIVIDER_HEIGHT,
            left: 0,
            behavior: "smooth",
          });

          if (laptopImageRef.current) {
            laptopImageRef.current.style.opacity = "0";
          }
          setScrollIdx(3);
        } else if (scrollTop >= pageHeight * 2 && scrollTop < pageHeight * 3) {
          // 현재 3페이지
          mainWrapperRef.current?.scrollTo({
            top: scrollTop + pageHeight + DIVIDER_HEIGHT,
            left: 0,
            behavior: "smooth",
          });
          setScrollIdx(4);
        } else if (scrollTop >= pageHeight * 3 && scrollTop < pageHeight * 4) {
          // 현재 4페이지
          mainWrapperRef.current?.scrollTo({
            top: scrollTop + pageHeight + DIVIDER_HEIGHT,
            left: 0,
            behavior: "smooth",
          });
          setScrollIdx(5);
        } else {
          // 현재 5페이지
          mainWrapperRef.current?.scrollTo({
            top: scrollTop + pageHeight + DIVIDER_HEIGHT,
            left: 0,
            behavior: "smooth",
          });
        }
      } else {
        // 스크롤 올릴 때
        if (scrollTop >= 0 && scrollTop < pageHeight) {
          //현재 1페이지
          mainWrapperRef.current?.scrollTo({
            top: 0,
            left: 0,
            behavior: "smooth",
          });
        } else if (scrollTop >= pageHeight && scrollTop < pageHeight * 2) {
          // 현재 2페이지
          mainWrapperRef.current!.scrollTo({
            top: scrollTop - pageHeight - DIVIDER_HEIGHT,
            left: 0,
            behavior: "smooth",
          });
          if (laptopImageRef.current) {
            laptopImageRef.current.style.bottom = "-80vh";
            laptopImageRef.current.style.left = "50%";
            laptopImageRef.current.style.transform =
              "translate(-50%, 0) scale(1.05)";
          }
          setScrollIdx(1);
        } else if (scrollTop >= pageHeight * 2 && scrollTop < pageHeight * 3) {
          // 현재 3페이지
          mainWrapperRef.current!.scrollTo({
            top: scrollTop - pageHeight - DIVIDER_HEIGHT,
            left: 0,
            behavior: "smooth",
          });
          if (laptopImageRef.current) {
            laptopImageRef.current.style.opacity = "1";
          }
          setScrollIdx(2);
        } else if (scrollTop >= pageHeight * 3 && scrollTop < pageHeight * 4) {
          // 현재 4페이지
          mainWrapperRef.current!.scrollTo({
            top: scrollTop - pageHeight - DIVIDER_HEIGHT,
            left: 0,
            behavior: "smooth",
          });
          setScrollIdx(3);
        } else {
          // 현재 5페이지
          mainWrapperRef.current!.scrollTo({
            top: scrollTop - pageHeight - DIVIDER_HEIGHT,
            left: 0,
            behavior: "smooth",
          });
          setScrollIdx(4);
        }
      }
      setTimeout(() => {
        isScrolling = false;
      }, 1000);
    };

    // 스와이프 이벤트 핸들러 함수
    const handleSwipe = (direction: string) => {
      const pageHeight = window.innerHeight;

      // 현재 스크롤 위치와 스와이프 방향에 따라 페이지 이동 처리
      if (direction === "left" && scrollIdx < 5) {
        mainWrapperRef.current?.scrollTo({
          top: mainWrapperRef.current.scrollTop + pageHeight + DIVIDER_HEIGHT,
          left: 0,
          behavior: "smooth",
        });
        setScrollIdx((prevIdx) => prevIdx + 1); // scrollIdx 증가
      } else if (direction === "right" && scrollIdx > 1) {
        mainWrapperRef.current?.scrollTo({
          top: mainWrapperRef.current.scrollTop - pageHeight - DIVIDER_HEIGHT,
          left: 0,
          behavior: "smooth",
        });
        setScrollIdx((prevIdx) => prevIdx - 1); // scrollIdx 감소
      }
    };

    // 모바일에서만 스와이프 이벤트 핸들러를 등록
    if (isMobile) {
      const swipeableRef = mainWrapperRef.current!;
      swipeableRef.addEventListener("swiped", (e: any) => {
        handleSwipe(e.dir);
      });
    }

    const wrapperRefCurrent = mainWrapperRef.current!;
    !isMobile &&
      wrapperRefCurrent.addEventListener("wheel", wheelHandler, {
        passive: false,
      });

    return () => {
      if (isMobile) {
        const swipeableRef = mainWrapperRef.current!;
        swipeableRef.removeEventListener("swiped", (e: any) => {
          handleSwipe(e.dir);
        });
      }
      wrapperRefCurrent.removeEventListener("wheel", wheelHandler);
    };
  }, [scrollIdx, isMobile]);

  return (
    <body className={`${noto.className} min-h-screen flex flex-col`}>
      <div
        ref={mainWrapperRef}
        className="h-screen overflow-hidden scroll-snap-y"
      >
        <Link href="/main/recruit">
          {isMobile ? (
            <button className="fixed h-12 text-sm transition-all duration-300 ease-in w-[100%] bottom-0 left-0 z-20 shadow-inner text-f5black-400 hover:bg-f5gray-400">
              {"건너뛰기 >>"}
            </button>
          ) : (
            <button className="fixed p-3 text-sm transition-all duration-300 ease-in top-5 right-10 rounded-2xl bg-f5gray-300 text-f5black-400 hover:bg-f5gray-400">
              {"건너뛰기 >>"}
            </button>
          )}
        </Link>
        <Dots scrollIdx={scrollIdx} />
        <div className="h-screen">
          <Image
            src="/images/laptop.png"
            ref={laptopImageRef} // 랩탑 이미지에 ref 추가
            alt="lattop"
            width={1588}
            height={1053}
            className="laptop fixed bottom-[-80vh] translate-x-[-50%] scale-105 transform transition-all duration-700 ease-in-out left-1/2"
          />
          <Page1 activePage={scrollIdx === 1 ? true : false} />
          <div className="w-[100%] h-1"></div>
          <Page2 activePage={scrollIdx === 2 ? true : false} />
          <div className="w-[100%] h-1"></div>
          <Page3 activePage={scrollIdx === 3 ? true : false} />
          <div className="w-[100%] h-1"></div>
          <Page4 activePage={scrollIdx === 4 ? true : false} />
          <div className="w-[100%] h-1"></div>
          <Page5 activePage={scrollIdx === 5 ? true : false} />
        </div>
      </div>
    </body>
  );
}
