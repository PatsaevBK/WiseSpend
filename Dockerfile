FROM ubuntu:22.04

ENV ANDROID_SDK_ROOT=/sdk
ENV DEBIAN_FRONTEND=noninteractive
ENV LANG=en_US.UTF-8
ENV LANGUAGE=en_US:en
ENV LC_ALL=en_US.UTF-8

USER root

RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        openjdk-17-jdk wget unzip git curl locales && \
    locale-gen en_US.UTF-8 && \
    rm -rf /var/lib/apt/lists/*

# Установка Android Command Line Tools
RUN mkdir -p $ANDROID_SDK_ROOT/cmdline-tools && \
    wget https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip -O tools.zip && \
    unzip tools.zip -d $ANDROID_SDK_ROOT/cmdline-tools && \
    rm tools.zip && \
    mv $ANDROID_SDK_ROOT/cmdline-tools/cmdline-tools $ANDROID_SDK_ROOT/cmdline-tools/latest

ENV PATH=$PATH:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$ANDROID_SDK_ROOT/platform-tools

# При необходимости создайте packages.txt с нужными компонентами
COPY packages.txt /sdk/packages.txt

RUN yes | sdkmanager --sdk_root=${ANDROID_SDK_ROOT} --licenses && \
    sdkmanager --sdk_root=${ANDROID_SDK_ROOT} --package_file=/sdk/packages.txt --verbose

# Добавление пользователя jenkins
RUN groupadd -g 1000 jenkins && \
    useradd -m -u 1000 -g jenkins jenkins && \
    chown -R jenkins:jenkins $ANDROID_SDK_ROOT

USER jenkins
WORKDIR /home/jenkins

