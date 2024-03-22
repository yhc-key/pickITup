// ReactCanvasConfetti.tsx
"use-client";
import canvasConfetti, {
  CreateTypes,
  GlobalOptions,
  Options,
} from "canvas-confetti";
import { CSSProperties, useRef, useEffect, useCallback } from "react";

interface ConfettiProps extends Options, GlobalOptions {
  fire?: any;
  reset?: any;
  width?: string | number;
  height?: string | number;
  className?: string;
  style?: CSSProperties;
  refConfetti?: (confetti: CreateTypes | null) => void;
  onDecay?: () => void;
  onFire?: () => void;
  onReset?: () => void;
}

export default function ReactCanvasConfetti({
  fire,
  reset,
  className,
  style,
  width,
  height,
  refConfetti,
  onDecay,
  onFire,
  onReset,
  ...confettiProps
}: ConfettiProps) {
  const refCanvas = useRef<HTMLCanvasElement>(null);
  const confettiRef = useRef<CreateTypes | null>(null);

  const fireConfetti = useCallback(() => {
    if (!confettiRef.current) return;

    onFire && onFire();

    const promise = confettiRef.current(confettiProps);

    promise &&
      promise.then(() => {
        onDecay && onDecay();
      });
  }, [onFire, onDecay, confettiProps]);

  const resetConfetti = useCallback(() => {
    if (!confettiRef.current) return;

    confettiRef.current.reset();

    onReset && onReset();
  }, [onReset]);

  useEffect(() => {
    if (!refCanvas.current) return;

    const { resize, useWorker } = confettiProps;
    const globalOptions: GlobalOptions = {
      resize: typeof resize === "undefined" ? true : resize,
      useWorker: typeof useWorker === "undefined" ? true : useWorker,
    };

    confettiRef.current = canvasConfetti.create(
      refCanvas.current,
      globalOptions
    );
    refConfetti && refConfetti(confettiRef.current);
  }, [confettiProps, refConfetti]);

  useEffect(() => {
    if (fire) {
      fireConfetti();
    }
  }, [fire, fireConfetti]);

  useEffect(() => {
    if (reset) {
      resetConfetti();
    }
  }, [reset, resetConfetti]);

  useEffect(() => {
    return () => {
      refConfetti && refConfetti(null);
    };
  }, [refConfetti]);

  return (
    <canvas
      ref={refCanvas}
      className="fixed inset-0 w-full h-screen pointer-events-none"
    />
  );
}
