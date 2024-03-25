"use client";
import { useState, useRef, useEffect } from "react";
import Link from "next/link";
import { Inter } from "next/font/google";
const inter = Inter({ subsets: ["latin"] });

import Dots from "@/components/onBoarding/dots";
import Page1 from "@/components/onBoarding/page1";
import Page2 from "@/components/onBoarding/page2";
import Page3 from "@/components/onBoarding/page3";
import Page4 from "@/components/onBoarding/page4";
import Page5 from "@/components/onBoarding/page5";

export default function Home() {
  const [scrollIdx, setScrollIdx] = useState<number>(1);
  const mainWrapperRef = useRef<HTMLDivElement>(null);
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
        if (scrollTop < pageHeight * 4) {
          mainWrapperRef.current?.scrollTo({
            top: scrollTop + pageHeight + DIVIDER_HEIGHT,
            left: 0,
            behavior: "smooth",
          });
          setScrollIdx((prevIdx) => Math.min(prevIdx + 1, 5));
          
        }
     
      } else {
        if (scrollTop > 0) {
          mainWrapperRef.current!.scrollTo({
            top: Math.max(scrollTop - pageHeight - DIVIDER_HEIGHT, 0),
            left: 0,
            behavior: "smooth",
          });
          setScrollIdx((prevIdx) => Math.max(prevIdx - 1, 1));
        }
      }
      setTimeout(() => {
        isScrolling = false;
      }, 1000);
    };


  
    const wrapperRefCurrent = mainWrapperRef.current!;
    wrapperRefCurrent.addEventListener("wheel", wheelHandler, {
      passive: false,
    });

    return () => {
      wrapperRefCurrent.removeEventListener("wheel", wheelHandler);
    };

  }, []);

  return (
    <body className={`${inter.className} min-h-screen flex flex-col`}>
      <div ref={mainWrapperRef} className="h-screen overflow-hidden scroll-snap-y">
        <Link href="/main/recruit">
          <button className="fixed p-3 text-sm transition-all duration-300 ease-in-out top-5 right-10 rounded-2xl bg-f5gray-300 text-f5black-400 hover:bg-f5gray-400">{"건너뛰기 >>"}</button>
        </Link>
        <Dots scrollIdx={scrollIdx} />
        <div className="h-screen">
          <Page1 />
          <div className="w-[100%] h-1"></div>
          <Page2 />
          <div className="w-[100%] h-1"></div>
          <Page3 />
          <div className="w-[100%] h-1"></div>
          <Page4 />
          <div className="w-[100%] h-1"></div>
          <Page5 />
        </div>
      </div>
    </body>
  );
}
