"use client";
import { useEffect, useState, useRef } from "react";

interface TimeBarProps {
  onNextClick: () => void;
  index: number;
}

function useInterval(callback: () => void, delay: number): void {
  // 최근에 들어온 callback을 저장할 ref 생성
  const savedCallback = useRef<() => void>();

  useEffect(() => {
    // callback이 바뀔 때마다 ref를 업데이트
    savedCallback.current = callback;
  }, [callback]);

  useEffect(() => {
    function tick() {
      // tick이 실행되면 callback 함수를 실행
      if (savedCallback.current) {
        savedCallback.current();
      }
    }

    // delay가 null이 아니라면
    if (delay !== null) {
      // delay에 맞춰 interval을 새로 실행
      let id = setInterval(tick, delay);
      // unmount될 때 clearInterval
      return () => clearInterval(id);
    }
  }, [delay]);
}

export default function TimeBar({ onNextClick, index }: TimeBarProps) {
  const [timer, setTimer] = useState<NodeJS.Timeout | null>(null);
  const [remainingTime, setRemainingTime] = useState(40);
  const [animationKey, setAnimationKey] = useState(0);

  // index가 변경될 때
  useEffect(() => {
    if (index !== 0) {
      setRemainingTime(40); // remainingTime을 10으로 초기화
      setAnimationKey((prevKey) => prevKey + 1); // 애니메이션 키 갱신
    }
  }, [index]);

  // 카운트다운 30초
  useInterval(() => {
    if (remainingTime <= 0) {
      onNextClick();
    } else {
      setRemainingTime((prev) => prev - 1);
    }
  }, 1000);

  // useEffect(() => {
  //   // 이전에 설정된 타이머가 있다면 제거
  //   if (timer) clearTimeout(timer);

  //   // index값이 10 이상인 경우에는 타이머 설정 끝
  //   if (index >= 10) return;

  //   setTimer(
  //     // 10초 후에 onNextClick 함수를 호출
  //     setTimeout(() => {
  //       if (index >= 10) return;
  //       onNextClick();
  //     }, 10000)
  //   );

  //   return () => {
  //     if (timer) clearTimeout(timer);
  //   };
  // }, [index, remainingTime, onNextClick, timer]);

  return (
    <div className="flex items-center justify-center">
      <div className="w-[65%] bg-f5gray-300 rounded-lg h-4">
        <div
          key={animationKey}
          className="h-4 rounded-lg animate-startGauge4 bg-gradient-to-r from-f5yellowgreen-200 to-f5green-300 "
        ></div>
      </div>
      <div className="ml-5 text-lg font-semibold align-middle text-f5black-400">
        {remainingTime} 초
      </div>
    </div>
  );
}
